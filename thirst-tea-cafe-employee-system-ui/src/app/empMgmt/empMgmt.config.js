export default function empMgmtConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('empMgmt', {
      url: '/empMgmt',
      component: 'empMgmt'
    });
}
