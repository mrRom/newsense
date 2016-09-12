'use strict';

newsense.factory('SimilarArticlesService', [
		'$http',
		'$q',
		function($http, $q) {

			return {

				fetchSimilarArticles : function(articleId) {
					return $http.get('stats/similar/' + articleId).then(
							function(response) {
								return response.data;
							}, function(errResponse) {
								console.error('Error while fetching articles');
								return $q.reject(errResponse);
							});
				}
			};

		} ]);

newsense.factory('SitesService', [ '$http', '$q', function($http, $q) {
	return {
		fetchAllSites : function() {
			return $http.get('listOfSites').then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while fetching sites');
				return $q.reject(errResponse);
			});
		},
		submitListOfSites : function(selection) {
			return $http.post('selectedSites', selection);
		},
		fetchSelectedSites : function() {
			return $http.get('selectedSites').then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while fetching selected sites');
				return $q.reject(errResponse);
			});
		},
		/*fetchSelectedSites : function() {
			return $http.get('selectedSites').then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while fetching sites');
				return $q.reject(errResponse);
			});
		}*/
	};
} ]);

newsense.factory('News', function($http) {
	var News = function() {
		this.Article = {
			id : null,
			title : '',
			url : '',
			text : '',
			site : ''
		};
		this.items = [];
		this.busy = false;
		this.after = 0;
	};

	News.prototype.nextPage = function() {
		if (this.busy)
			return;
		this.busy = true;

		var url = 'news/more/' + this.after;
		$http.get(url).success(function(response) {
			$.merge(this.items, response);
			this.after += 1;
			this.busy = false;
		}.bind(this));
	};

	return News;
});

newsense.factory('NotificationService', function() {
	var count = [];
	var tweetFeed = new EventSource("notify");

	// the factory returns a function waiting for a callback that will handle
	// the controller
	return {
		getTweets : function(callback) {
			tweetFeed.addEventListener("message", callback, false);
		}
	};
});

newsense.factory('UserService', function($http) {
	var service = {};

	service.createUser = createUser;

	return service;

	function createUser(username, password) {
		var userdata = '{"username":"' + username + '", "password":"' + password + '"}';
		console.log(userdata);
		return $http.post('/register', userdata).then(handleSuccess,
				handleError('Error creating user'));
	}

	// private functions

	function handleSuccess(res) {
		return {
			success : true
		};
	}

	function handleError(error) {
		return function() {
			return {
				success : false,
				message : error
			};
		};
	}
});