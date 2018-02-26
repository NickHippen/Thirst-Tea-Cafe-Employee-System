export default function adminConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('admin', {
      url: '/admin',
      component: 'admin'
    });
}
