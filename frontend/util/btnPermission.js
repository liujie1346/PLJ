import Vue from 'vue'

/**权限指令**/
const has = Vue.directive('has', {
  bind: function (el, binding, vnode) {
    let permissionList = binding.value;//在页面上绑定的 对应权限显示
    let roles = ['6','2'];//获取的用户权限
    el.style.display="none";
    for (let i = 0; i < roles.length; i++) {
      if (permissionList.includes(roles[i])) {
        el.style.display="block";
        break;
      }
    }
  }
});

export default has;

