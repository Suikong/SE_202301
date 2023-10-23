import axios from "axios";
 
const app = new Vue({
  el: "#app",
  data() {
    return {
      expression: "",
      result: "",
    };
  },
  methods: {
    clear() {
      this.expression = "";
      this.result = "";
    },
    equal() {
      // 发送请求到后端
      axios
        .post("/api/calculate", { expression: this.expression })
        .then((response) => {
          // 获取计算结果
          this.result = response.data.result;
        });
    },
  },
});