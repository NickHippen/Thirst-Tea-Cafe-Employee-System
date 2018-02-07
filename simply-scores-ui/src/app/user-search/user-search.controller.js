export default class {

  constructor($log, $state, AlertHandler, UserService, UserSearchService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, UserService, UserSearchService, LoadingService});
    this.searchForm = {
    };
    this.searchResults = [];
    // this.searchResults.push({
    //   userId: 1,
    //   userName: 'abc',
    //   region: 'Midwest',
    //   createDate: '11/24/2017'
    // });
  }

  submitSearch() {
    this.LoadingService.loading = true;
    this.UserSearchService.searchUsers(this.searchForm)
      .then(response => {
        this.LoadingService.loading = false;
        this.searchResults = response.data;
      })
      .catch(error => {
        this.LoadingService.loading = false;
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
      });
  }

}
