export default function viewConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('view', {
      url: '/view',
      component: 'view'
    });
}
