export default function userViewConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('user-view', {
      url: '/user/:userId',
      component: 'userView'
    });
}
