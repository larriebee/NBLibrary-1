package com.netbuilder.nblibrary.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.netbuilder.nblibrary.businessParameters.BusinessParameters;
import com.netbuilder.nblibrary.domain.Borrower;
import com.netbuilder.nblibrary.domain.Item;
import com.netbuilder.nblibrary.domain.Loan;
import com.netbuilder.nblibrary.service.BorrowerServiceTemplate;
import com.netbuilder.nblibrary.service.ItemServiceTemplate;
import com.netbuilder.nblibrary.service.LoanServiceTemplate;
import com.netbuilder.nblibrary.service.ValidatorServiceTemplate;

@Controller
@SessionAttributes({ "borrower", "item", "loan" })
public class MainController {

	@Autowired
	private BorrowerServiceTemplate borrowerService;

	@Autowired
	private ItemServiceTemplate itemService;

	@Autowired
	private LoanServiceTemplate loanService;

	@Autowired
	private ValidatorServiceTemplate validatorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:/index.html";
	}

	@RequestMapping(value = {"/index.html", "/index.htm", "/index"}, method = RequestMethod.GET)
	public String homePage() {

		checkOverdue();

		return "index";
	}

	@RequestMapping(value = {"/aboutUs.html", "/aboutUs.htm", "/aboutUs"}, method = RequestMethod.GET)
	public String aboutUsPage() {

		return "aboutUs";
	}

	/*
	 * Borrower Mappings
	 */

	// Need to add javascript to validate the form entries
	@RequestMapping(value = {"/addBorrower.html", "/addBorrower.htm", "/addBorrower"}, method = RequestMethod.GET)
	public ModelAndView newBorrowerForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		ModelAndView mav = new ModelAndView("addBorrower");
		Borrower borrower = new Borrower();
		mav.getModelMap().put("errorMessage", errorMessage);
		mav.getModelMap().put("borrower", borrower);
		return mav;
	}

	@RequestMapping(value = {"/addBorrower.html", "/addBorrower.htm", "/addBorrower"}, method = RequestMethod.POST)
	public ModelAndView addBorrower(
			@ModelAttribute("borrower") Borrower borrower, BindingResult result) {
		ModelAndView mav = new ModelAndView("redirect:/addBorrower.html");

		if (validatorService.validateText(borrower.getName())) {
			if (validatorService.validateEmail(borrower.getEmail())) {
				if (!borrowerService.retrieveByEmail(borrower.getEmail()).isEmpty()) {
					mav.getModelMap().put("errorMessage", "There is already a Borrower with that Email Address");
				} else {
					mav = new ModelAndView("redirect:/listBorrowers.html");
					mav.getModelMap().put("errorMessage", "The new Borrower was successfully added");
					borrowerService.add(borrower);
				}
			} else {
				mav.getModelMap().put("errorMessage",
						"Please enter a valid Email Address");
			}
		} else {
			mav.getModelMap().put("errorMessage", "Please enter a valid Name");
		}
		return mav;
	}

	@RequestMapping(value = {"/removeBorrower.html", "/removeBorrower.htm", "/removeBorrower"}, method = RequestMethod.GET)
	public String removeBorrowerForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage,
			Model model) {
		model.addAttribute("errorMessage", errorMessage);
		return "removeBorrower";
	}

	@RequestMapping(value = {"/removeBorrowerSubmission.html", "/removeBorrowerSubmission.htm", "/removeBorrowerSubmission"}, method = RequestMethod.GET)
	public ModelAndView removeBorrower(
			@RequestParam(value = "email", required = true) String email) {
		List<Borrower> borrowers = borrowerService.retrieveByEmail(email);

		return removeBorrowerCheck(borrowers);
	}

	private ModelAndView removeBorrowerCheck(List<Borrower> borrowers) {
		ModelAndView mav = new ModelAndView("redirect:/listBorrowers.html");

		if (borrowers.size() > 1) {
			mav.getModelMap().put("errorMessage",
					"More than one borrower with that email");

		} else if (borrowers.isEmpty()) {
			mav.getModelMap().put("errorMessage", "Borrower does not exist");

		} else if (!loanService.retrieveOpenLoansForBorrower(
				borrowers.get(0).getBorrowerId()).isEmpty()) {
			mav.getModelMap()
					.put("errorMessage",
							"The Borrower still has open loans, return items before continuing");

		} else {
			borrowerService.remove(borrowers.get(0));
			mav.getModelMap().put("errorMessage",
					"The Borrower has been removed");

		}

		return mav;
	}

	@RequestMapping(value = {"/updateBorrower.html", "/updateBorrower.htm", "/updateBorrower"}, method = RequestMethod.GET)
	public String updateBorrowerForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage,
			Model model) {
		return "updateBorrower";
	}

	@RequestMapping(value = {"/updateBorrowerSubmission.html", "/updateBorrowerSubmission.htm", "/updateBorrowerSubmission"}, method = RequestMethod.GET)
	public String updateBorrowerSubmission(
			@RequestParam(value = "email", required = true) String email,
			Model model) {
		List<Borrower> borrowers = borrowerService.retrieveByEmail(email);

		if (borrowers.size() > 1) {
			model.addAttribute("errorMessage",
					"More than one borrower with that email");

			return "updateBorrower";
		} else if (borrowers.isEmpty()) {
			model.addAttribute("errorMessage", "Borrower does not exist");

			return "updateBorrower";
		} else {
			model.addAttribute("borrower", borrowers.get(0));
			return "updateABorrower";
		}
	}

	// Need to implement JavaScript to validate the form entries
	@RequestMapping(value = {"/updateBorrower.html", "/updateBorrower.htm", "/updateBorrower"}, method = RequestMethod.POST)
	public String updateBorrower(@ModelAttribute("borrower") Borrower borrower,
			BindingResult result, Model model) {
		borrowerService.update(borrower);

		model.addAttribute("errorMessage", "The Borrower has been updated");

		return "redirect:/listBorrowers.html";
	}

	@RequestMapping(value = {"/listBorrowers.html", "/listBorrowers.htm", "/listBorrowers"}, method = RequestMethod.GET)
	public ModelAndView listBorrowers(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		List<Borrower> borrowers = borrowerService.retrieveAll();

		ModelAndView mav = new ModelAndView("listBorrowers");

		mav.getModelMap().put("borrowers", borrowers);

		mav.getModelMap().put("errorMessage", errorMessage);

		return mav;
	}

	@RequestMapping(value = {"/listBorrowersSubmission.html", "/listBorrowersSubmission.htm", "/listBorrowersSubmission"}, method = RequestMethod.GET)
	public ModelAndView listBorrowersSubmission(
			@RequestParam(value = "buttonSelected", required = true) String buttonSelected,
			@RequestParam(value = "borrowerSelection", required = false) String borrowerSelection) {

		ModelAndView mav = new ModelAndView("redirect:/listBorrowers.html");
		mav.getModelMap()
				.put("errorMessage",
						"Please select a Borrower if you want to Update or Remove them");
		if (buttonSelected.equals("Add Borrower")) {
			mav = new ModelAndView("redirect:/addBorrower.html");

		} else if (borrowerSelection != null) {
			if (buttonSelected.equals("Update Borrower")) {
				mav = new ModelAndView("updateABorrower");
				mav.getModelMap().put(
						"borrower",
						borrowerService.retrieveById(Integer
								.parseInt(borrowerSelection)));
			} else if (buttonSelected.equals("Remove Borrower")) {
				List<Borrower> borrowers = new ArrayList<Borrower>();
				borrowers.add(borrowerService.retrieveById(Integer
						.parseInt(borrowerSelection)));
				mav = removeBorrowerCheck(borrowers);
			}
		}
		return mav;
	}

	/*
	 * Item Mappings
	 */

	@RequestMapping(value = {"/addItem.html", "/addItem.htm", "/addItem"}, method = RequestMethod.GET)
	public ModelAndView newItemForm(@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		ModelAndView mav = new ModelAndView("addItem");
		Item item = new Item();
		mav.getModelMap().put("item", item);
		mav.getModelMap().put("errorMessage", errorMessage);
		
		return mav;
	}

	// Need to implement JavaScript to validate the form entries
	@RequestMapping(value = {"/addItem.html", "/addItem.htm", "/addItem"}, method = RequestMethod.POST)
	public ModelAndView addItem(
			@ModelAttribute("item") Item item, BindingResult result) {
		
		ModelAndView mav = new ModelAndView("redirect:/addItem.html");
		
		if (validatorService.validateText(item.getTitle())) {
			if(!itemService.retrieveByTitle(item.getTitle()).isEmpty()) {
				mav.getModelMap().put("errorMessage", "There is already an Item with that Title");
			} else {
				if(validatorService.validateText(item.getAuthor())) {
					if(validatorService.validateText(item.getEdition())) {
						if(validatorService.validateDate(item.getPublishDate())) {
							mav = new ModelAndView("redirect:/listItems.html");
							mav.getModelMap().put("errorMessage", "The new Item was successfully added");
							item.setLoanable(true);
							itemService.add(item);
							
						} else {
							mav.getModelMap().put("errorMessage", "Please enter a valid date in the format mm/dd/yyyy");
						}
					} else {
						mav.getModelMap().put("errorMessage", "Please enter a valid edition");
					}
				} else {
					mav.getModelMap().put("errorMessage", "Please enter a valid author");
				}
			}
		} else {
			mav.getModelMap().put("errorMessage", "Please enter a valid title");
		}

		return mav;
	}

	@RequestMapping(value = {"/removeItem.html", "/removeItem.htm", "/removeItem"}, method = RequestMethod.GET)
	public String removeItemForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage,
			Model model) {
		model.addAttribute("errorMessage", errorMessage);
		return "removeItem";
	}

	@RequestMapping(value = {"/removeItemSubmission.html", "/removeItemSubmission.htm", "/removeItemSubmission"}, method = RequestMethod.GET)
	public ModelAndView removeItem(
			@RequestParam(value = "title", required = true) String title,
			Model model) {
		List<Item> items = itemService.retrieveByTitle(title);

		return removeItemCheck(items);
	}

	private ModelAndView removeItemCheck(List<Item> items) {
		ModelAndView mav = new ModelAndView("redirect:/listItems.html");

		if (items.size() > 1) {
			mav.getModelMap().put("errorMessage",
					"More than one item with that title");

		} else if (items.isEmpty()) {
			mav.getModelMap().put("errorMessage", "Item does not exist");

		} else if (!items.get(0).isLoanable()) {
			mav.getModelMap()
					.put("errorMessage",
							"The item is still on loan, return the item before continuing");

		} else {
			itemService.remove(items.get(0));
			mav.getModelMap().put("errorMessage", "The item has been removed");

		}

		return mav;
	}

	@RequestMapping(value = {"/updateItem.html", "/updateItem.htm", "/updateItem"}, method = RequestMethod.GET)
	public String updateItemForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage,
			Model model) {

		return "updateItem";
	}

	@RequestMapping(value = {"/updateItemSubmission.html", "/updateItemSubmission.htm", "/updateItemSubmission"}, method = RequestMethod.GET)
	public String updateItemSubmission(
			@RequestParam(value = "title", required = true) String title,
			Model model) {
		List<Item> items = itemService.retrieveByTitle(title);

		if (items.size() > 1) {
			model.addAttribute("errorMessage",
					"More than one item with that email");

			return "updateItem";
		} else if (items.isEmpty()) {
			model.addAttribute("errorMessage", "Item does not exist");

			return "updateItem";
		} else {
			model.addAttribute("item", items.get(0));
			return "updateAnItem";
		}
	}

	// Need to implement JavaScript to validate the form entries
	@RequestMapping(value = {"/updateItem.html", "/updateItem.htm", "/updateItem"}, method = RequestMethod.POST)
	public String updateItem(@ModelAttribute("item") Item item,
			BindingResult result, Model model) {
		itemService.update(item);

		model.addAttribute("errorMessage", "The Item has been updated");

		return "redirect:/listItems.html";
	}

	@RequestMapping(value = {"/listItems.html", "/listItems.htm", "/listItems"}, method = RequestMethod.GET)
	public ModelAndView listItems(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		List<Item> items = itemService.retrieveAll();

		ModelAndView mav = new ModelAndView("listItems");
		mav.getModelMap().put("items", items);
		mav.getModelMap().put("errorMessage", errorMessage);

		return mav;
	}

	@RequestMapping(value = {"/listItemsSubmission.html", "/listItemsSubmission.htm", "/listItemsSubmission"}, method = RequestMethod.GET)
	public ModelAndView listItemSubmission(
			@RequestParam(value = "buttonSelected", required = true) String buttonSelected,
			@RequestParam(value = "itemSelection", required = false) String itemSelection) {

		ModelAndView mav = new ModelAndView("redirect:/listItems.html");
		mav.getModelMap().put("errorMessage",
				"Please select an Item if you want to Update or Remove it");
		if (buttonSelected.equals("Add Item")) {
			mav = new ModelAndView("redirect:/addItem.html");

		} else if (itemSelection != null) {
			if (buttonSelected.equals("Update Item")) {
				mav = new ModelAndView("updateAnItem");
				mav.getModelMap().put(
						"item",
						itemService.retrieveById(Integer
								.parseInt(itemSelection)));
			} else if (buttonSelected.equals("Remove Item")) {
				List<Item> items = new ArrayList<Item>();
				items.add(itemService.retrieveById(Integer
						.parseInt(itemSelection)));
				mav = removeItemCheck(items);
			}
		}
		return mav;
	}

	/*
	 * Loan Mappings
	 */

	@RequestMapping(value = {"/addLoan.html", "/addLoan.htm", "/addLoan"}, method = RequestMethod.GET)
	public ModelAndView addLoan(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		ModelAndView mav = new ModelAndView("addLoanSearchBorrowers");
		return mav;
	}

	@RequestMapping(value = {"/addLoanItem.html", "/addLoanItem.htm", "/addLoanItem"}, method = RequestMethod.GET)
	public ModelAndView addLoanItem(
			@RequestParam(value = "errorMessage", required = false) String errorMessage,
			@RequestParam(value = "email", required = true) String email) {
		ModelAndView mav = new ModelAndView("addLoanSearchBorrowers");

		List<Borrower> borrowers = borrowerService.retrieveByEmail(email);

		if (borrowers.size() > 1) {
			mav.getModelMap().put("errorMessage",
					"More than one borrower with that email");

		} else if (borrowers.isEmpty()) {
			mav.getModelMap().put("errorMessage", "Borrower does not exist");

		} else if (borrowers.get(0).isBlacklisted()) {
			mav.getModelMap().put("errorMessage", "Borrower is Blacklisted");

		} else if (loanService.retrieveOpenLoansForBorrower(
				borrowers.get(0).getBorrowerId()).size() >= BusinessParameters.LOAN_LIMIT) {
			mav.getModelMap().put("errorMessage",
					"Borrower currently has too many items out on loan");

		} else if (!loanService.retrieveOverdueLoansForBorrower(
				borrowers.get(0).getBorrowerId()).isEmpty()) {
			mav.getModelMap().put("errorMessage",
					"Borrower currently has overdue loans");

		} else {
			mav = new ModelAndView("addLoanSearchItems");
			mav.getModelMap().put("borrower", borrowers.get(0));
		}

		return mav;
	}

	@RequestMapping(value = {"/addLoanConfirmation.html", "/addLoanConfirmation.htm", "/addLoanConfirmation"}, method = RequestMethod.GET)
	public ModelAndView addLoanConfirmations(
			@RequestParam(value = "title", required = true) String title,
			@ModelAttribute("borrower") Borrower borrower, BindingResult result) {
		ModelAndView mav = new ModelAndView("addLoanSearchItems");

		List<Item> items = itemService.retrieveByTitle(title);

		if (items.size() > 1) {
			mav.getModelMap().put("errorMessage",
					"More than one item with that email");

		} else if (items.isEmpty()) {
			mav.getModelMap().put("errorMessage", "Item does not exist");

		} else if (!itemService.isLoanable(items.get(0).getItemId())) {
			mav.getModelMap().put("errorMessage", "Item is not loanable");

		} else {
			mav = new ModelAndView("addLoanSubmission");
			mav.getModelMap().put("borrower", borrower);
			mav.getModelMap().put("item", items.get(0));
			mav.getModelMap().put("loan", new Loan());
		}

		return mav;
	}

	@RequestMapping(value = {"/addLoanSubmission.html", "/addLoanSubmission.htm", "/addLoanSubmission"}, method = RequestMethod.POST)
	public String addLoan(@ModelAttribute("loan") Loan loan,
			BindingResult result, Model model) {
		loanService.add(loan);
		itemService.toggleLoanable(loan.getItemId());

		model.addAttribute("errorMessage", "The Loan has been created");

		return "redirect:/listLoans.html";
	}

	@RequestMapping(value = {"/removeLoan.html", "/removeLoan.htm", "/removeLoan"}, method = RequestMethod.GET)
	public ModelAndView removeLoanForm(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		ModelAndView mav = new ModelAndView("removeLoan");
		return mav;
	}

	@RequestMapping(value = {"/removeLoanBorrower.html", "/removeLoanBorrower.htm", "/removeLoanBorrower"}, method = RequestMethod.GET)
	public ModelAndView removeLoanBorrower(
			@RequestParam(value = "email", required = true) String email) {
		ModelAndView mav = new ModelAndView("removeLoan");

		List<Borrower> borrowers = borrowerService.retrieveByEmail(email);

		if (borrowers.size() > 1) {
			mav.getModelMap().put("errorMessage",
					"More than one borrower with that email");

		} else if (borrowers.isEmpty()) {
			mav.getModelMap().put("errorMessage", "Borrower does not exist");

		} else {
			mav = new ModelAndView("removeLoanBorrower");
			List<Loan> loans = loanService
					.retrieveOpenLoansForBorrower(borrowers.get(0)
							.getBorrowerId());
			mav.getModelMap().put("loans", loans);
			mav.getModelMap().put("borrower", borrowers.get(0));

			// for each loan in loans, get the item and them put them into a
			// list
			List<Item> items = new ArrayList<Item>();

			for (Loan loan : loans) {
				Item item = itemService.retrieveById(loan.getItemId());
				items.add(item);
			}

			mav.getModelMap().put("items", items);

		}

		return mav;
	}

	@RequestMapping(value = {"/removeLoanSubmission.html", "/removeLoanSubmission.htm", "/removeLoanSubmission"}, method = RequestMethod.POST)
	public ModelAndView removeLoan(
			@RequestParam(value = "returnedLoans", required = true) String[] returnedLoans) {
		return removeLoanCheck(returnedLoans, "listLoans.html");
	}

	private ModelAndView removeLoanCheck(String[] returnedLoans, String view) {
		ModelAndView mav = new ModelAndView("redirect:" + view);

		String returnedLoanIds = "";

		for (String loanId : returnedLoans) {
			Loan loan = loanService.retrieveById(Integer.parseInt(loanId));
			returnedLoanIds = returnedLoanIds + " " + loanId;
			itemService.toggleLoanable(loan.getItemId());
			loanService.remove(loan);
		}

		mav.getModelMap().put("errorMessage",
				"The following loan(s) have been returned: " + returnedLoanIds);

		return mav;
	}

	@RequestMapping(value = {"/listLoans.html", "/listLoans.htm", "/listLoans"}, method = RequestMethod.GET)
	public ModelAndView listLoans(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		
		checkOverdue();
		
		List<Loan> loans = loanService.retrieveAll();

		ModelAndView mav = listBorrowerItemNamesForLoans(loans, "listLoans");

		mav.getModelMap().put("errorMessage", errorMessage);

		return mav;
	}

	private ModelAndView listBorrowerItemNamesForLoans(List<Loan> loans,
			String view) {
		ModelAndView mav = new ModelAndView(view);

		mav.getModelMap().put("loans", loans);

		List<Item> items = new ArrayList<Item>();
		List<Borrower> borrowers = new ArrayList<Borrower>();

		for (Loan loan : loans) {
			Item item = itemService.retrieveById(loan.getItemId());
			items.add(item);
			Borrower borrower = borrowerService.retrieveById(loan
					.getBorrowerId());
			borrowers.add(borrower);
		}

		mav.getModelMap().put("items", items);
		mav.getModelMap().put("borrowers", borrowers);

		return mav;
	}

	@RequestMapping(value = {"/listLoansSubmission.html", "/listLoansSubmission.htm", "/listLoansSubmission"}, method = RequestMethod.GET)
	public ModelAndView listLoansSubmission(
			@RequestParam(value = "buttonSelected", required = true) String buttonSelected,
			@RequestParam(value = "loanSelection", required = false) String[] loanSelection,
			HttpServletRequest request) {

		String referer = request.getHeader("Referer");

		if (referer.contains("Overdue")) {
			referer = "listOverdueLoans.html";
		} else {
			referer = "listLoans.html";
		}

		ModelAndView mav = new ModelAndView("redirect:" + referer);
		mav.getModelMap().put("errorMessage",
				"Please select a Loan if you want to Return an Item");
		if (buttonSelected.equals("Sign Out Item")) {
			mav = new ModelAndView("redirect:/addLoan.html");

		} else if (loanSelection != null) {
			if (buttonSelected.equals("Return Item")) {
				mav = removeLoanCheck(loanSelection, referer);
			}
		}
		return mav;
	}

	@RequestMapping(value = {"/listOverdueLoans.html", "/listOverdueLoans.htm", "/listOverdueLoans"}, method = RequestMethod.GET)
	public ModelAndView listOverudeLoans(
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		
		checkOverdue();
		
		List<Loan> loans = loanService.retrieveOverdueLoans();

		ModelAndView mav = listBorrowerItemNamesForLoans(loans,
				"listLoansOverdue");

		mav.getModelMap().put("errorMessage", errorMessage);

		return mav;
	}

	@RequestMapping(value = "/get_email_list", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	List<String> getEmailList(@RequestParam("term") String query) {

		List<String> listOfBorrowers = new ArrayList<String>();
		List<Borrower> borrowers = borrowerService.retrieveAll();
		Iterator<Borrower> iterator = borrowers.iterator();
		query = query.toUpperCase();

		while (iterator.hasNext()) {
			Borrower borrower = iterator.next();
			if (borrower.getEmail().toUpperCase().contains(query)) {
				listOfBorrowers.add(borrower.getEmail());
			}

		}

		if (listOfBorrowers.isEmpty()) {
			listOfBorrowers.add("No email containing '" + query.toLowerCase()
					+ "' was found.");
		}

		return listOfBorrowers;
	}

	@RequestMapping(value = "/get_title_list", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	List<String> getTitleList(@RequestParam("term") String query) {

		List<String> listOfTitles = new ArrayList<String>();
		List<Item> items = itemService.retrieveAll();
		Iterator<Item> iterator = items.iterator();

		query = query.toUpperCase();

		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item.getTitle().toUpperCase().contains(query)) {
				listOfTitles.add(item.getTitle());
			}

		}

		if (listOfTitles.isEmpty()) {
			listOfTitles.add("No title containing '" + query.toLowerCase()
					+ "' was found.");
		}

		return listOfTitles;
	}

	// checks whether an item has gone overdue, if so, mark as overdue and add a
	// strike to the borrower
	// if borrower has reached the strike limit, mark them as blacklisted
	// Should be called at midnight everyday
	public void checkOverdue() {
		List<Loan> loans = loanService.retrieveAll();

		for (Loan loan : loans) {
			if ((!loan.isOverdue())
					&& loan.getEndDate().before(
							loanService.calculateCurrentDate())) {
				Borrower borrower = borrowerService.retrieveById(loan
						.getBorrowerId());
				int newBorrowerStrikes = borrower.getStrikes() + 1;
				borrower.setStrikes(newBorrowerStrikes);

				if (newBorrowerStrikes >= BusinessParameters.STRIKE_LIMIT) {
					borrower.setBlacklisted(true);
				}

				borrowerService.update(borrower);

				loan.setOverdue(true);
				loanService.update(loan);

			}
		}
	}

}
