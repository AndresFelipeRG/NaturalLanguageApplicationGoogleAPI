/*global QUnit*/

sap.ui.define([
	"com/sap/financial/sentiment/scores/FinancialSentimentScores/controller/FinancialScores.controller"
], function (Controller) {
	"use strict";

	QUnit.module("FinancialScores Controller");

	QUnit.test("I should test the FinancialScores controller", function (assert) {
		var oAppController = new Controller();
		oAppController.onInit();
		assert.ok(oAppController);
	});

});