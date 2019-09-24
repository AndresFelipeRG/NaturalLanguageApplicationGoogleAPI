/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"com/sap/financial/sentiment/scores/FinancialSentimentScores/test/integration/AllJourneys"
	], function () {
		QUnit.start();
	});
});