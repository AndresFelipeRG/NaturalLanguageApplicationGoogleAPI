sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel"
], function (Controller) {
	"use strict";

	return Controller.extend("com.sap.financial.sentiment.scores.FinancialSentimentScores.controller.FinancialScores", {
		onInit: function () {
				var oModel = this.getOwnerComponent().getModel("tableData");
				this.getView().setModel(oModel, "DataModel");
		}
	});
});