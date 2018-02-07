export default function userSearchConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('user-search', {
      url: '/users',
      component: 'userSearch'
    });
}
