import scoreLeaderboardTemplate from './score-leaderboard.html';
import ScoreLeaderboardCtrl from './score-leaderboard.controller';

export default {
  template: scoreLeaderboardTemplate,
  controller: ScoreLeaderboardCtrl,
  bindings: {
    scores: '<',
    noRank: '<',
    showSong: '<'
  }
};
