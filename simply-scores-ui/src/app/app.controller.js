export default class {

  constructor($log, AppService) {
    'ngInject';
    angular.extend(this, {$log, AppService});

    this.recentScores = [];
    this.globalLeaderboard = [];

    this.recentScores.push({
      songName: 'Starlight',
      name: 'abc',
      score: '100.0',
      date: '11/25/2017'
    });

    this.globalLeaderboard.push({
      name: 'abc'
    });
  }

}
