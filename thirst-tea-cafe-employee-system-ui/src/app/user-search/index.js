import userSearchConfig from './user-search.config';
import userSearch from './user-search.component';
import UserSearchService from './user-search.service';

export default angular.module('tt-employee.userSearch', [
])
.config(userSearchConfig)
.component('userSearch', userSearch)
.service('UserSearchService', UserSearchService)
.name;
