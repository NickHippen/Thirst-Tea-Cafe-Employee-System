export default function shiftMgmtConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('shiftMgmt', {
      url: '/shiftMgmt',
      component: 'shiftMgmt'
    });
}
