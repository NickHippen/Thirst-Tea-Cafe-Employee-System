export default function registerConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('register', {
      url: '/login/register',
      component: 'register'
    });
}
