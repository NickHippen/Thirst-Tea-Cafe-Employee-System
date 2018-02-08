export default class {

  constructor($log, $state, AlertHandler, UserService, SongSearchService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, UserService, SongSearchService, LoadingService});
    this.searchForm = {
    };
    this.searchResults = [];
    // this.searchResults.push({
    //   songId: 1,
    //   songName: '12 Little Fragments of Silence',
    //   songPack: 'Betwixt & Between',
    //   difficulty: 14
    // });
    // this.searchResults.push({
    //   songId: 1,
    //   songName: '12 Little Fragments of Silence',
    //   songPack: 'Betwixt & Between',
    //   difficulty: 14
    // });
    // this.searchResults.push({
    //   songId: 1,
    //   songName: '12 Little Fragments of Silence',
    //   songPack: 'Betwixt & Between',
    //   difficulty: 14
    // });
    // this.searchResults.push({
    //   songId: 1,
    //   songName: '12 Little Fragments of Silence',
    //   songPack: 'Betwixt & Between',
    //   difficulty: 14
    // });
  }

  submitSearch() {
    this.LoadingService.loading = true;
    this.SongSearchService.searchSongs(this.searchForm)
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
