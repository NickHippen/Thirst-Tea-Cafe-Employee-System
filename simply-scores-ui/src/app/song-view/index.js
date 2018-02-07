import songViewConfig from './song-view.config';
import songView from './song-view.component';
import SongViewService from './song-view.service';

export default angular.module('simplyScores.songView', [
])
.config(songViewConfig)
.component('songView', songView)
.service('SongViewService', SongViewService)
.name;
