import { defineStore } from 'pinia';
import axios from 'axios';
import customAxios from '@/axios';
import router from "@/router/index.js";

export const useUserStore = defineStore('user', {
  state: () => ({
    isAuthenticated: false,
    error: null,
  }),

  actions: {
    async login(employeeNo, pwd) {
      this.error = null;
      try {
        const response = await axios.post('http://localhost:8090/api/v1/login', {
          userEmployeeNo: employeeNo,
          userPwd: pwd,
        });

        const accessToken = response.headers["authorization"];
        const refreshToken = response.headers["refresh-token"];

        if (accessToken) {
          this.isAuthenticated = true;

          // JWT 토큰 로컬 스토리지에 저장
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('refreshToken', refreshToken);
        } else {
          this.error = 'Token not found';
        }
      } catch (error) {
        this.error = error.response?.data?.message || '아이디 또는 비밀번호를 잘못 입력했습니다.';
      }
    },

    async logout() {
      this.error = null;
      try {
        const response = await customAxios.post(`logout`);

      } catch (error) {
        alert(`로그아웃 실패하였습니다.`);
      }

      this.isAuthenticated = false;
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');

      await router.push("/");
    },
  },
});