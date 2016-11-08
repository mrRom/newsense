'use strict';

newsense.controller('MoreCtrl', function($scope) {
	$scope.showModal = false;
	$scope.toggleModal = function() {
		$scope.showModal = !$scope.showModal;
	};
});

newsense.controller('SitesCtrl', [ 
        '$scope',
        'SitesService',
        function($scope, SitesService) {
			var self = this;
			self.ListOfSites = [];
			self.selection = [];
			// TODO
			// Refactoring required
			self.fetchAllSites = function() {
				SitesService.fetchAllSites().then(
					function(d) {
						self.ListOfSites = d;
					}, function(errResponse) {
						console.error('Error while fetching ListOfSites');
				});
			};
			self.fetchSelectedSites = function() {
				SitesService.fetchSelectedSites().then(
					function(d) {
						self.selection = d;
						console.log(d)
					}, function(errResponse) {
						console.error('Error while fetching Selected Sites');
				});
			};
			self.fetchAllSites();
			self.fetchSelectedSites();
			self.toggleSelection = function toggleSelection(site) {
			    var idx = self.selection.indexOf(site);
			    // is currently selected
			    if (idx > -1) {
			      self.selection.splice(idx, 1);
			    }
			    // is newly selected
			    else {
			      self.selection.push(site);
			    }
			  };
			
			self.submitListOfSites = function() {
				SitesService.submitListOfSites(self.selection);
			}
} ]);

newsense.controller('ShowHideSimilarCtrl', [
		'$scope',
		'SimilarArticlesService',
		function($scope, SimilarArticlesService) {
			var self = this;
			$scope.showMe = false;
			$scope.showHideSimilar = function(articleId) {
				if ($scope.showMe == false) {
					self.Article = {
						id : null,
						title : '',
						url : '',
						description : '',
						publishDate : ''
					};
					self.Articles = [];
					self.fetchSimilarArticles(articleId);
				}
				$scope.showMe = !$scope.showMe;
			};

			self.fetchSimilarArticles = function(articleId) {
				SimilarArticlesService.fetchSimilarArticles(articleId).then(
						function(d) {
							self.Articles = d;
						}, function(errResponse) {
							console.error('Error while fetching Articles');
						});
			};
		} ]);

newsense.controller('ArticlesScrollCtrl', function($scope, News) {
	$scope.news = new News();
});

newsense.controller('NotificationCtrl', ['$scope', 'NotificationService', function($scope, NotificationService) {
	$scope.count = 0;
	this.recieveNotifications = function() {
		NotificationService.getTweets(function(response) {
	        var count = JSON.parse(response.data);
	        console.log(count);
	        $scope.count = count;
	        $scope.$apply();
	    });
	}
	$scope.reloadRoute = function() {
		   window.location.reload();
		}
	this.recieveNotifications();
}]);

newsense.controller('navigation',
		function($rootScope, $http, $location, $route) {
			
			var self = this;

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						$rootScope.authenticated = true;
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}).error(function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}

			authenticate();

			self.credentials = {};
			self.login = function() {
				authenticate(self.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						$location.path("/");
						self.error = false;
						$rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						$location.path("/login");
						self.error = true;
						$rootScope.authenticated = false;
					}
				})
			};

			self.logout = function() {
				$http.post('logout', {}).finally(function() {
					$rootScope.authenticated = false;
					$location.path("/");
				});
			}
});

newsense.controller('home', function($http) {
	var self = this;
	$http.get('/resource/').success(function(data) {
		self.greeting = data;
	})
});

newsense.controller('registration', function($location, UserService) {
	var self = this;
	self.dataLoading = true;
	self.register = function() {
		self.dataLoading = true;
        UserService.createUser(self.username, self.password, self.email)
            .then(function (response) {
                if (response.success) {
                    $location.path('/login');
                } else {
                    self.dataLoading = false;
                }
            });
	};
});
