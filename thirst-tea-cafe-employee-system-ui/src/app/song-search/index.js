import songSearchConfig from './song-search.config';
import songSearch from './song-search.component';
import SongSearchService from './song-search.service';

import './index.scss';

export default angular.module('tt-employee.songSearch', [
])
.config(songSearchConfig)
.component('songSearch', songSearch)
.service('SongSearchService', SongSearchService)
.name;
