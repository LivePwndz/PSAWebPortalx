package com.swifta.mats.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class ManageUserModule {
	private String strSessionVarUManage;
	//private String strSessionVarDetails;
	public static final String DEFAULT_UMANAGE_SESSION_VAR = null;
	public static final String SESSION_UMANAGE = "umanage_session";
	public static final String SESSION_VAR_UMANAGE_SEARCH = "search_user";
	public static final String SESSION_VAR_UMANAGE_SEARCH_RESULTS = "search_results";
	public static final String SESSION_VAR_UMANAGE_USER_DETAILS = "user_details";
	private TextField tfUname;
	
	
	
	public ManageUserModule(){
		this.strSessionVarUManage = (String) UI.getCurrent().getSession().getAttribute(SESSION_UMANAGE);
		//this.strSessionVarDetails = (String) Login.CUR_SESSION_OBJ.getAttribute(UMANAGE_SESSION_DETAILS);
	}
	public VerticalLayout getSearchContainer(){
				VerticalLayout searchContainer = new VerticalLayout();
				searchContainer.setSizeUndefined();
				searchContainer.setSpacing(false);
				searchContainer.setStyleName("c_search_user");
				searchContainer.setMargin(new MarginInfo(false, true, false, true));
		
				FormLayout searchForm = new FormLayout();
				searchForm.setSizeUndefined();
				searchForm.setSpacing(true);
				searchForm.setMargin(false);
				searchForm.setStyleName("search_user_form");
				
				Embedded emb = new Embedded(null,new ThemeResource("img/search_user_icon.png"));
				emb.setDescription("Search users");
				emb.setStyleName("search_user_img");
				emb.setSizeUndefined();
				
				
				Label lbSearch = new Label("Search users by: ");
				lbSearch.setSizeUndefined();
				lbSearch.setStyleName("label_search_user");
				lbSearch.setSizeUndefined();
				
				VerticalLayout searchUserHeader = new VerticalLayout();
				searchUserHeader.setHeightUndefined();
				searchUserHeader.setMargin(false);
				searchUserHeader.setSpacing(true);
				searchUserHeader.addComponent(emb);
				searchUserHeader.addComponent(lbSearch);
				searchContainer.addComponent(searchUserHeader);
				
				
				TextField tfUid = new TextField();
				tfUid.setCaption("User ID");
				
				tfUname = new TextField();
				tfUname.setCaption("Username");
				
				TextField tfFn = new TextField();
				tfFn.setCaption("First Name");
				
				TextField tfLn = new TextField();
				tfLn.setCaption("Last Name");
				
				TextField tfOthers = new TextField();
				tfOthers.setCaption("Others");
				
				Button btnSearch = new Button("Search");
				btnSearch.setIcon(FontAwesome.SEARCH);
				//ThemeResource r = new ThemeResource("img/search_small.png");
				
				//btnSearch.setIcon(r);
				//btnSearch.setHeight("60px");
				//btnSearch.setWidth("183px");
				//searchForm.addComponent(lbSearch);
				searchForm.addComponent(searchUserHeader);
				searchForm.addComponent(tfUid);
				searchForm.addComponent(tfUname);
				searchForm.addComponent(tfFn);
				searchForm.addComponent(tfLn);
				searchForm.addComponent(tfOthers);
				searchForm.addComponent(btnSearch);
				searchContainer.addComponent(searchForm);
				searchContainer.setComponentAlignment(searchForm, Alignment.TOP_RIGHT);
				
				
				
				btnSearch.addClickListener(new Button.ClickListener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = -5894920456172825127L;
		
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getSession().setAttribute(SESSION_UMANAGE, SESSION_VAR_UMANAGE_SEARCH_RESULTS);
						UI.getCurrent().getNavigator().navigateTo(WorkArea.WORK_AREA+"/search_user_results");
					}
				});
				
			

		
		
		return searchContainer;
	}
	
	
	public VerticalLayout getSearchResults(){
		VerticalLayout searchContainer = new VerticalLayout();
		
		VerticalLayout searchResultsContainer = new VerticalLayout();
		searchResultsContainer.setSizeUndefined();
		searchResultsContainer.setSpacing(true);
		searchResultsContainer.setMargin(new MarginInfo(false, true, true, true));
		
		
		VerticalLayout searchUserHeader = new VerticalLayout();
		searchUserHeader.setWidth("100%");
		searchUserHeader.setHeightUndefined();
		searchUserHeader.setMargin(true);
		searchUserHeader.setSpacing(true);
		searchContainer.setStyleName("c_u_search_results");
		
		Embedded emb = new Embedded(null,new ThemeResource("img/search_user_1.png"));
		emb.setDescription("Search users");
		emb.setStandby("search_user_img");
		searchUserHeader.addComponent(emb);
		searchUserHeader.setComponentAlignment(emb, Alignment.TOP_RIGHT);
		
		Button btnSearch = new Button();
		btnSearch.setDescription("Search");
		//ThemeResource r = new ThemeResource("img/search_small.png");
		//btnSearch.setIcon(r);
		btnSearch.setIcon(FontAwesome.SEARCH);
		searchUserHeader.addComponent(btnSearch);
		searchUserHeader.setComponentAlignment(btnSearch, Alignment.TOP_RIGHT);
		
		Label lbSearchResults = new Label("Search Results...");
		lbSearchResults.setSizeUndefined();
		lbSearchResults.setStyleName("label_search_results");
		searchUserHeader.addComponent(lbSearchResults);
		searchUserHeader.setComponentAlignment(lbSearchResults, Alignment.TOP_RIGHT);
		
		//searchContainer.addComponent(searchUserHeader);
		
		
		SearchUserModule sum = new SearchUserModule();
		IndexedContainer container = sum.queryBackEnd();
		
		
		PagedTableCustom tb = new PagedTableCustom("Search results for: \"admin\"(Summary)");
		
		
		tb.setContainerDataSource(container);
		tb.setColumnIcon(" ", FontAwesome.CHECK_SQUARE_O);
		//tb.setPageLength(5);
		tb.setStyleName("tb_u_search_results");
		
		HorizontalLayout pnUserSearchResults = tb.createControls();
		pnUserSearchResults.setSizeFull();
		pnUserSearchResults.setMargin(false);
		pnUserSearchResults.setSpacing(false);
		
		//int pageCur = tb.getCurrentPage();
		//tb.previousPage();
		//tb.nextPage();
		//int pageTotal = tb.getTotalAmountOfPages();
		
		searchResultsContainer.addComponent(pnUserSearchResults);
		searchResultsContainer.addComponent(tb);
		//tb.setHeight("219px");
		
		VerticalLayout actionBulkC = new VerticalLayout();
		actionBulkC.setWidth("100%");
		actionBulkC.setStyleName("c_action_bulk");
		
		CheckBox chkAll = new CheckBox();
		chkAll.setCaption("Select All");
		
		ComboBox cmbBulk = new ComboBox("Bulk Action");
		cmbBulk.addItem("Delete");
		cmbBulk.addItem("Link");
		
		actionBulkC.addComponent(chkAll);
		actionBulkC.addComponent(cmbBulk);
		
		
		searchResultsContainer.addComponent(actionBulkC);
		searchContainer.addComponent(searchResultsContainer);
		searchContainer.setComponentAlignment(searchResultsContainer, Alignment.TOP_RIGHT);
		
		
		
		
		
		btnSearch.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7182067560976379938L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().setAttribute(SESSION_UMANAGE, SESSION_VAR_UMANAGE_SEARCH_RESULTS);
				UI.getCurrent().getNavigator().navigateTo(WorkArea.WORK_AREA+"/manage_user");
				
			}
		});
		
		
		
		return searchContainer;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public VerticalLayout getUserOperationContainer(){
		VerticalLayout userOperationContainer = new VerticalLayout();
		
		HorizontalLayout formContainer = new HorizontalLayout();
		formContainer.setSizeUndefined();
		formContainer.setSpacing(true);
		formContainer.setMargin(new MarginInfo(false, true, true, true));
		
		FormLayout operationFormUserProfile = new FormLayout();
		operationFormUserProfile.setSizeUndefined();
		operationFormUserProfile.setSpacing(true);
		operationFormUserProfile.setMargin(true);
		operationFormUserProfile.setStyleName("operation_form_user_profile");
		
		FormLayout operationFormAccountProfile = new FormLayout();
		operationFormAccountProfile.setSizeUndefined();
		operationFormAccountProfile.setSpacing(true);
		operationFormAccountProfile.setMargin(true);
		operationFormAccountProfile.setStyleName("operation_form_account_profile");
		
		formContainer.addComponent(operationFormUserProfile);
		formContainer.addComponent(operationFormAccountProfile);
		
		Embedded emb = new Embedded(null,new ThemeResource("img/user_operation_1.png"));
		emb.setDescription("Profile operations.");
		emb.setStyleName("user_operation_img");
		
		Label lbOperationU = new Label("User");
		Label lbOperationA = new Label("Account");
		Label lbOperationHeader = new Label("Choose Operation...");
		lbOperationHeader.setStyleName("label_operation_header");
		lbOperationHeader.setSizeUndefined();
		
		VerticalLayout userOperationHeader = new VerticalLayout();
		userOperationHeader.setWidthUndefined();
		userOperationHeader.setHeightUndefined();
		userOperationHeader.setMargin(new MarginInfo(true, true, false, true));
		userOperationHeader.setSpacing(true);
		
		userOperationHeader.addComponent(emb);
		userOperationHeader.addComponent(lbOperationHeader);
		userOperationHeader.setComponentAlignment(lbOperationHeader, Alignment.TOP_CENTER);
		userOperationHeader.setComponentAlignment(emb, Alignment.TOP_LEFT);
		userOperationContainer.addComponent(userOperationHeader);
		
		
		Button btnDetails = new Button("Details");
		btnDetails.setStyleName(ValoTheme.BUTTON_PRIMARY);
		Button btnEdit = new Button("Edit");
		Button btnLink = new Button("Link");
		Button btnULog = new Button("Activity Log");
		Button btnDelete = new Button("Delete");
		
		btnDetails.setIcon(FontAwesome.ALIGN_JUSTIFY);
		btnEdit.setIcon(FontAwesome.EDIT);
		btnLink.setIcon(FontAwesome.LINK);
		btnDelete.setIcon(FontAwesome.RECYCLE);
		
		Button btnAuthentication = new Button("Authentication");
		Button btnAuthorization = new Button("Authorization");
		Button btnACLog = new Button("Change Log");
		
		operationFormUserProfile.addComponent(lbOperationU);
		operationFormUserProfile.addComponent(btnDetails);
		operationFormUserProfile.addComponent(btnEdit);
		operationFormUserProfile.addComponent(btnLink);
		operationFormUserProfile.addComponent(btnDelete);
		operationFormUserProfile.addComponent(btnULog);
		
		
		
		operationFormAccountProfile.addComponent(lbOperationA);
		operationFormAccountProfile.addComponent(btnAuthentication);
		operationFormAccountProfile.addComponent(btnAuthorization);
		operationFormAccountProfile.addComponent(btnACLog);
		
		
		
		userOperationContainer.addComponent(formContainer);
		//userOperationContainer.setWidth("100%");
		//userOperationContainer.setHeight("100%");
		userOperationContainer.setSizeUndefined();
		userOperationContainer.setStyleName("u_operation_container");
		
		
		if(strSessionVarUManage.equals(anObject) == null){
			operationFormUserProfile.setEnabled(false);
			operationFormAccountProfile.setEnabled(false);
			emb.setEnabled(false);
			lbOperationHeader.setEnabled(false);
			btnDetails.setEnabled(false);
			btnEdit.setEnabled(false);
			btnLink.setEnabled(false);
			btnDelete.setEnabled(false);
			btnULog.setEnabled(false);
			btnAuthentication.setEnabled(false);
			btnAuthorization.setEnabled(false);
			btnACLog.setEnabled(false);
		}
		
		btnDetails.addClickListener(new Button.ClickListener(){

		
			private static final long serialVersionUID = -4281417789039287912L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				Login.CUR_SESSION_OBJ.setAttribute(UMANAGE_SESSION_DETAILS, "details");
				UI.getCurrent().getNavigator().navigateTo(WorkArea.WORK_AREA+"/user_details");
				
			}
			
		});
		

		return userOperationContainer;
	}*/
	
	
	
	
	
	
	
	
	public VerticalLayout getUserDetailsContainer(){
		
		/*
		 * 
		 * TODO Fetch user details....
		 * 
		 * 
		 * 
		 * 
		 */
		
		String curTable = (String) UI.getCurrent().getSession().getAttribute(BtnActionsClickable.SESSION_ACTIONS_TABLE);
		String curUID = (String) UI.getCurrent().getSession().getAttribute(BtnActionsClickable.SESSION_ACTIONS_TABLE);
		UserDetailsModule udm = new UserDetailsModule();
		String uDetails[] = udm.getUserDetails(curTable, curUID);
		
		
		/*
		 * 
		 * 
		 * 
		 * 
		 * Populate forms
		 */

			VerticalLayout cUDetails = new VerticalLayout();
			cUDetails.setMargin(new MarginInfo(false, true, true, true));
			
			cUDetails.setStyleName("c_u_details");
			cUDetails.setSizeUndefined();
			
			FormLayout  cUAccountDetails = new FormLayout();
			FormLayout  cUAuthenticationlDetails = new FormLayout();
			FormLayout  cULogDetails = new FormLayout();
			
			HorizontalLayout cCHeader = new HorizontalLayout();
			cCHeader.setSizeUndefined();
			cCHeader.setMargin(new MarginInfo(false, true, true, true));
			
			Label lbCHeader = new Label("Details of Sevo");
			lbCHeader.setStyleName("label_c_header");
			lbCHeader.setSizeUndefined();
			
			
			cCHeader.addComponent(lbCHeader);
			
			//TODO Values below are dummy. Need to fetch field captions and respective values.

			
			String strTbName = "Personal";
			
			String[] arrTfCaption =  new String[]{"UID", "First Name", "Last Name"};
			String[] arrTfVal =  new String[]{"001", "Amama", "Yoweri"};
			
			String[] arrOptCaption = new String[]{"Sex"};
			String[] arrOptSelVal = new String[]{"Male"};
			
			String[] arrComboCaption =  new String[]{"Type"};
			String[] arrComboSelVal =  new String[]{"Transaction"};
			
			String[] arrDfCaption =  new String[]{"DOC"};
			String[] arrDfVal =  new String[]{"5/10/2014"};
			
			
			HorizontalLayout cPerAccAuthInfo = new HorizontalLayout();;
			cPerAccAuthInfo.addComponent(getDetailsForm( strTbName,arrTfCaption, arrTfVal, arrOptCaption, arrOptSelVal, arrComboCaption, arrComboSelVal, arrDfCaption, arrDfVal));
			//cPerAccAuthInfo.addComponent(getDetailsForm("Account", new String[]{"AID", "Name", "Type", "DOC"}, new String[]{"001", "Amama", "Transaction", "13/10/2014"}, new String[]{"Status"}, new String[]{"Active"}));
			//cPerAccAuthInfo.addComponent(getDetailsForm(new String[]{"Authentication","Username"}));
			//cPerAccAuthInfo.addComponent(getDetailsForm(new String[]{"Log"}));
			
			
			
			
			cUDetails.addComponent(cCHeader);
			cUDetails.setComponentAlignment(cCHeader, Alignment.TOP_CENTER);
			
			cUDetails.addComponent(cPerAccAuthInfo);
			
			
			
			
		
		return cUDetails; 
	}
	
	
	@SuppressWarnings("deprecation")
	private FormLayout getDetailsForm(String strTbName, String[] arrTfCaption, final String[] arrTfVal, String[] arrOptCaption, String[] arrOptSelVal, String[] arrComboCaption, String[] arrComboSelVal, String[] arrDfCaption, String[] arrDfVal){
		
		
		FormLayout  cUPersonalDetails = new FormLayout();
		cUPersonalDetails.setMargin(true);
		cUPersonalDetails.setSpacing(false);
		cUPersonalDetails.setStyleName("frm_details_personal_info");
		cUPersonalDetails.setSizeUndefined();
		
		
		
		
		Label lbGen;
		TextField tfGen;
		OptionGroup opt;
		ComboBox combo;
		PopupDateField dF;
		
		
		final String btnEditId = arrTfVal[2]+"_edit";
		final String btnSaveId = arrTfVal[2]+"_save";
		
		//Holders of editable form components
		final ArrayList<TextField> arrLTfEditable = new ArrayList<TextField>();
		final ArrayList<OptionGroup> arrLOptEditable = new ArrayList<OptionGroup>();
		final ArrayList<ComboBox> arrLComboEditable = new ArrayList<ComboBox>();
		final ArrayList<PopupDateField> arrLDfEditable = new ArrayList<PopupDateField>();
		
		//Holders of editable form components original values
		final ArrayList<String> arrLTfEditableVals = new ArrayList<String>();
		final ArrayList<String> arrLOptEditableVals = new ArrayList<String>();
		final ArrayList<String> arrLComboEditableVals = new ArrayList<String>();
		final ArrayList<String> arrLDfEditableVals = new ArrayList<String>();
		
		
		
		int iTf;
		int iOpt;
		int iCombo;
		int iDf;
		
		//For testing purposes, we assume that every first item of each for category is read-only, hence the isReadOnly = 0
		int isReadOnlyTf = 0;
		int isReadOnlyOpt = 1;
		int isReadOnlyCombo = -1;
		
		
		lbGen = new Label();
		lbGen.setStyleName(ValoTheme.LABEL_H4);
		lbGen.setStyleName("label_frm_header");
		lbGen.setCaption(strTbName);
		cUPersonalDetails.addComponent(lbGen);
		
		//set TextField(tf) form objects
		
		for(iTf = 0; iTf < arrTfVal.length; iTf++){
			tfGen = new TextField();
			tfGen.setCaption(arrTfCaption[iTf]);
			tfGen.setValue(arrTfVal[iTf]);
			tfGen.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
			tfGen.setReadOnly(true);
			cUPersonalDetails.addComponent(tfGen);
			
			if(iTf != isReadOnlyTf){
				arrLTfEditable.add(tfGen);
				arrLTfEditableVals.add(arrTfVal[iTf]);
			}
		}
		
		//Set OptionGroup(opt) form objects
		
		for(iOpt = 0; iOpt < arrOptSelVal.length; iOpt++){
			opt = new OptionGroup();
			opt.setCaption(arrOptCaption[iOpt]);
			opt.addItem(arrOptSelVal[iOpt]);
			opt.select(arrOptSelVal[iOpt]);
			opt.setReadOnly(true);
			
			if(iOpt != isReadOnlyOpt){
				arrLOptEditable.add(opt);
				arrLOptEditableVals.add(arrOptSelVal[iOpt]);
			}
			
			cUPersonalDetails.addComponent(opt);
		}
		
		
		//Set ComboBox(combo) form objects
		
		for(iCombo = 0; iCombo < arrComboSelVal.length; iCombo++){
			combo = new ComboBox();
			combo.setCaption(arrComboCaption[iCombo]);
			combo.addItem(arrComboSelVal[iCombo]);
			combo.select(arrComboSelVal[iCombo]);
			combo.setReadOnly(true);
			
			if(iCombo != isReadOnlyCombo){
				arrLComboEditable.add(combo);
				arrLComboEditableVals.add(arrComboSelVal[iCombo]);
			}
			
			cUPersonalDetails.addComponent(combo);
		}
		
		
		

		//Set InlineDateField(dF) form objects
		
		for(iDf= 0; iDf < arrDfVal.length; iDf++){
			dF = new PopupDateField();
			dF.setCaption(arrDfCaption[iDf]);
			dF.setValue(new Date(arrDfVal[iDf]));
			dF.setReadOnly(true);
			
			if(iDf != isReadOnlyCombo){
				arrLDfEditable.add(dF);
				arrLDfEditableVals.add(arrDfVal[iDf]);
			}
			
			cUPersonalDetails.addComponent(dF);
		}
		
		
		
		
		
		
		
		
		
		
		final Button btnEdit = new Button();
		btnEdit.setId(btnEditId);
		btnEdit.setIcon(FontAwesome.EDIT);
		btnEdit.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnEdit.setStyleName("btn_link");
		
		final Button btnCancel = new Button();
		btnCancel.setId(btnEditId);
		btnCancel.setIcon(FontAwesome.UNDO);
		btnCancel.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnCancel.setStyleName("btn_link");
		btnCancel.setVisible(false);
		
		final HorizontalLayout cBtnEditCancel = new HorizontalLayout();
		cBtnEditCancel.setSizeUndefined();
		cBtnEditCancel.addComponent(btnEdit);
		
		cUPersonalDetails.addComponent(cBtnEditCancel);
		
		
		
		btnEdit.addClickListener(new Button.ClickListener() {
			
			
			private static final long serialVersionUID = -6544444429248747390L;

			@Override
			public void buttonClick(ClickEvent event) {
				TextField curTf;
				OptionGroup curOpt;
				ComboBox curCombo;
				PopupDateField curDf;
				/*
				 * Prepare all Editable fields (Entire form) for editing.
				 */
				if(event.getButton().getId().equals(btnEditId)){
					for(int i = 0; i < arrLTfEditable.size(); i++){
						curTf = arrLTfEditable.get(i);
						curTf.setReadOnly(false);
						curTf.removeStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
						
					}
					
					for(int i = 0; i < arrLOptEditable.size(); i++){
						curOpt = arrLOptEditable.get(i);
						curOpt.setReadOnly(false);
						
						//TODO Values below are dummy. Need to fetch applicable field values for system users to use when editing.
						curOpt.addItem("Female");
						curOpt.addItem("Male");
					}
					
					for(int i = 0; i < arrLComboEditable.size(); i++){
						curCombo = arrLComboEditable.get(i);
						curCombo.setReadOnly(false);
						
						//TODO Values below are dummy. Need to fetch applicable field value for system users to choose
						curCombo.addItem("Finance Controller");
						curCombo.addItem("Customer Care Operator");
					}
					
					
					for(int i = 0; i < arrLDfEditable.size(); i++){
						curDf = arrLDfEditable.get(i);
						curDf.setReadOnly(false);
					}
					
					
					
					/*By Default, btnCancel is not visible, until btnEdit is clicked.
					 * Only until then is it added and visible.
					 */
					
					if(!btnCancel.isVisible()){
						event.getButton().setId(btnSaveId);
						event.getButton().setIcon(FontAwesome.SAVE);
						btnCancel.setVisible(true);
						cBtnEditCancel.addComponent(btnCancel);	
					}
					
					
					
				}else{
					if(event.getButton().getId().equals(btnSaveId)){
						//TODO commit (save) changes i.e, send changes back to the server.
						
						//Remove undo button (btnCancel)
						btnCancel.setVisible(false);
						
						//Reset all Editable fields to readOnly after saving to the server
						disableEditableFields(arrLTfEditable, arrLOptEditable, arrLComboEditable, arrLDfEditable);
						
						
						//Reset btnEdit id to btnIdEdit and caption(icon) to FontAwesome.EDIT
						btnEdit.setId(btnEditId);
						btnEdit.setIcon(FontAwesome.EDIT);
						
					}
				}
				
			}
		});
		
		
		
		btnCancel.addClickListener(new Button.ClickListener() {
			
			
			private static final long serialVersionUID = 7719883177456399112L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				resetForm(arrLTfEditable,  arrLTfEditableVals, arrLOptEditable, arrLOptEditableVals, arrLComboEditable, arrLComboEditableVals, arrLDfEditable, arrLDfEditableVals);
				btnEdit.setId(btnEditId);
				btnEdit.setIcon(FontAwesome.EDIT);
				btnCancel.setVisible(false);
				
			}
		});
		
		
		
		return cUPersonalDetails;
	}
	
	
	@SuppressWarnings("deprecation")
	private void resetForm(ArrayList<TextField> arrLTf,  ArrayList<String> arrLTfVals, ArrayList<OptionGroup> arrLOpt, ArrayList<String> arrLOptVals, ArrayList<ComboBox> arrLCombo, ArrayList<String> arrLComboSelVals, ArrayList<PopupDateField>arrLDf, ArrayList<String>arrLDfVals){
			int i = 0;
			
			for(TextField tf: arrLTf){
				tf.setValue(arrLTfVals.get(i));
				tf.setStyleName(ValoTheme.BUTTON_BORDERLESS);
				tf.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(OptionGroup opt: arrLOpt){
				opt.setValue(arrLOptVals.get(i));
				opt.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(ComboBox combo: arrLCombo){
				combo.setValue(arrLComboSelVals.get(i));
				combo.setReadOnly(true);
				i++;
			}
			
			i = 0; //reset the index
			for(PopupDateField dF: arrLDf){
				//dF.setValue(new Date(arrLDfVals.get(i)));
				dF.setReadOnly(true);
				i++;
			}
			
						
	}
	
	
	private void disableEditableFields(ArrayList<TextField> arrLTf, ArrayList<OptionGroup> arrLOpt, ArrayList<ComboBox> arrLCombo, ArrayList<PopupDateField>arrLDf){
		
		
		int i = 0;
		
		for(TextField tf: arrLTf){
			
			tf.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			tf.setReadOnly(true);
			i++;
		}
		
		i = 0; //reset the index
		for(OptionGroup opt: arrLOpt){
			
			opt.setReadOnly(true);
			i++;
		}
		
		i = 0; //reset the index
		for(ComboBox combo: arrLCombo){
			
			combo.setReadOnly(true);
			i++;
		}
		
		i = 0; //reset the index
		for(PopupDateField dF: arrLDf){
			dF.setReadOnly(true);
			i++;
		}
		
	}
	
}
