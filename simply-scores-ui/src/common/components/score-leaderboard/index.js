import scoreLeaderboard from './score-leaderboard.component';
import ScoreLeaderboardService from './score-leaderboard.service';

export default angular.module('simplyScores.common.components.scoreleaderboard', [
])
.component('scoreLeaderboard', scoreLeaderboard)
.service('ScoreLeaderboardService', ScoreLeaderboardService)
.name;
