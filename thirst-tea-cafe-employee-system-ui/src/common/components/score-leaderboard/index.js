import scoreLeaderboard from './score-leaderboard.component';
import ScoreLeaderboardService from './score-leaderboard.service';

export default angular.module('tt-employee.common.components.scoreleaderboard', [
])
.component('scoreLeaderboard', scoreLeaderboard)
.service('ScoreLeaderboardService', ScoreLeaderboardService)
.name;
