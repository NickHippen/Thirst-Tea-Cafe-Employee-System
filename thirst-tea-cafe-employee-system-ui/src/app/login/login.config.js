export default function loginConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('login', {
      url: '/login',
      component: 'login'
    });
}
