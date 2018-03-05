/*
 *  Starter code for University of Waterloo CS349 - Spring 2017 - A3.
 *	Refer to the JS examples shown in lecture for further reference.
 *  Note: this code uses ECMAScript 6.
 *  Updated 2017-07-12.
 */
 
 /*
 *  The code have been updated by Ching Chuan (Charlie) Wu for the cs349 - Spring 2017 - A3. This is a copy of the solution to the A3. Please notice 
 *	that it includes the starter code written by school and thus please do not distributed the code. 
 *  Updated 2018-03-04.
 */
	
"use strict";

// Get your own API key from https://uwaterloo.ca/api/register
var apiKey = '65c3aae4e47a06150c5096b04e1045d1';

(function(exports) {

	/* A Model class */
    class AppModel {
		constructor() {
			// Array to store infromation from end point
			this._observers = [];
			this.course_id = []
			this.subject = [];
			this.catalog_number = [];
			this.title = [];
			this.units = [];
			this.description = [];
			this.academic_level = [];
			this.length;
		}

        // Call this function to retrieve data from a UW API endpoint
        loadData(endpointUrl) {
            var that = this;
            $.getJSON(endpointUrl + "?key=" + apiKey,
                function (data) {
					//Save the data fromt eh endpoint
					for (var i = 0; i < data.data.length; i++) {
						that.course_id.push(data.data[i].course_id);
						that.subject.push(data.data[i].subject);
						that.catalog_number.push(data.data[i].catalog_number);
						that.title.push(data.data[i].title);
						that.units.push(data.data[i].units);
						that.description.push(data.data[i].description);
						that.academic_level.push(data.data[i].academic_level);
					}
					that.length = data.data.length;
                    
                    that.notify("");
                }
            );
        }
		
		// Add observer functionality to AppModel objects:
		
		// Add an observer to the list
		addObserver(observer) {
            this._observers.push(observer);
            observer.updateView(this, null);
        }
		
		// Notify all the observers on the list
		notify(args) {
            _.forEach(this._observers, function(obs) {
                obs.updateView(this, args);
            });
        }
    }

    /*
     * A view class.
     * model:  the model we're observing
     * div:  the HTML div where the content goes
     */
    class AppView {
		constructor(model, div) {
			this.model = model;
			this.div = div;
			model.addObserver(this);
		}
		
        updateView(obs, args) {
			$(this.div).html("");
			var head = false;
			
			var output = args;
			
			for (var i = 0; i < this.model.length; i++) {
				var display = true;
				if ($(".CI_Comparison:checked").val() == "Greater") {
					if (this.model.course_id[i] <= $("#Course_Id").val()) {
						display = false;
					}
				} else if ($(".CI_Comparison:checked").val() == "Less") {
					if (this.model.course_id[i] >= $("#Course_Id").val()) {
						display = false;
					}
				} else if ($(".CI_Comparison:checked").val() == "Equal") {
					display = false;
					if (this.model.course_id[i] == $("#Course_Id").val()) {
						display = true;
					}
				} else {
					display = true;
				}
				if (display == true) {
					if ($(".CN_Comparison:checked").val() == "Greater") {
						if (this.model.catalog_number[i] <= $("#Catalog_Number").val()) {
							display = false;
						}
					} else if ($(".CN_Comparison:checked").val() == "Less") {
						if (this.model.catalog_number[i] >= $("#Catalog_Number").val()) {
							display = false;
						}
					} else if ($(".CN_Comparison:checked").val() == "Equal") {
						display = false;
						if (this.model.catalog_number[i] == $("#Catalog_Number").val()) {
							display = true;
						}
					} else {
						display = true;
					}
				}
				if (display == true) {
					if (($("#Subject").val() == this.model.subject[i]) || ($("#Subject").val() == '')) {
						display = true;
					} else {
						display = false;
					}
				}
				if (display == true) {
					if (($("#Title").val() == this.model.title[i]) || ($("#Title").val() == '')) {
						display = true;
					} else {
						display = false;
					}
				}
				if (display == true) {
					if (head == false) {
						output = output + "<table>";
						output = output + "<tr>";
						output = output + "<th><p>Course Id</p></th>";
						output = output + "<th><p>Subject</p></th>";
						output = output + "<th><p>Catalog Number</p></th>";
						output = output + "<th><p>Title</p></th>";
						output = output + "<th><p>Units</p></th>";
						output = output + "<th><p>Description</p></th>";
						output = output + "<th><p>Academic Level</p></th>";
						output = output + "</tr>";
						head = true;
					}
					output = output + "<tr>";
					output = output + "<th><p>" + this.model.course_id[i] + "</p></th>";
					output = output + "<th><p>" + this.model.subject[i] + "</p></th>";
					output = output + "<th><p>" + this.model.catalog_number[i] + "</p></th>";
					output = output + "<th><p>" + this.model.title[i] + "</p></th>";
					output = output + "<th><p>" + this.model.units[i] + "</p></th>";
					output = output + "<th><p>" + this.model.description[i] + "</p></th>";
					output = output + "<th><p>" + this.model.academic_level[i] + "</p></th>";
					output = output + "</tr>";
				}
			}
			if (head == true) {
				output = output + "</table>";
			}
			$(this.div).append(output);
        };        
    }

	/*
		Function that will be called to start the app.
		Complete it with any additional initialization.
	*/
    exports.startApp = function() {
        var model = new AppModel();
        var view = new AppView(model, "div#viewContent");
		
		$("#Search").click(function() {
			if ($("#Subject").val() == "") {
				model.length = 0;
				model.notify("<p>Please Specify the Subject</p>");
			} else {
				model.loadData("https://api.uwaterloo.ca/v2/courses/" + $("#Subject").val() + ".json");
			}
		});
		
		var $loading = $('#loading').hide();
		$(document)
		.ajaxStart(function () {
			$loading.show();
		})
		.ajaxStop(function () {
			$loading.hide();
		});
	}

})(window);
