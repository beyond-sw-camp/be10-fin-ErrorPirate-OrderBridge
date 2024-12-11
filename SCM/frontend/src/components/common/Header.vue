<script setup>
import chatbotIcon from '@/assets/chatbotIcon.svg';
import notificationIcon from '@/assets/notificationIcon.svg';
import loginIcon from '@/assets/loginIcon.svg';
import basicIcon from '@/assets/basicIcon.svg';
import salesIcon from '@/assets/salesIcon.svg';
import productionIcon from '@/assets/productionIcon.svg';
import orderIcon from '@/assets/orderIcon.svg';
import statisticsIcon from '@/assets/statisticsIcon.svg'
</script>

<template>
  <b-navbar toggleable="lg" type="dark" class="header">
    <!-- 로고 -->
    <b-navbar-brand href="#">
      <img src="@/images/logo.png" alt="OrderBridge Logo" class="logo-img" />
    </b-navbar-brand>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown>
          <template #button-content><basicIcon class="icon"/>기본등록</template>
          <b-dropdown-item href="#">거래처 등록</b-dropdown-item>
          <b-dropdown-item href="#">창고 등록</b-dropdown-item>
          <b-dropdown-item href="#">품목 등록</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><salesIcon class="icon" />영업관리</template>
          <b-dropdown-item href="/quotation">견적 관리</b-dropdown-item>
          <b-dropdown-item href="/quotation/input">주문서 관리</b-dropdown-item>
          <b-dropdown-item href="#">판매 관리</b-dropdown-item>
          <b-dropdown-item href="#">출하지시서 관리</b-dropdown-item>
          <b-dropdown-item href="#">출하 관리</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><orderIcon class="icon" />주문관리</template>
          <b-dropdown-item href="#">발주서 관리</b-dropdown-item>
          <b-dropdown-item href="#">구매서 관리</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><productionIcon class="icon" />생산관리</template>
          <b-dropdown-item href="#">작업지시서 관리</b-dropdown-item>
          <b-dropdown-item href="#">생산불출 관리</b-dropdown-item>
          <b-dropdown-item href="#"><RouterLink to="/productionReceiving" active-class="active" replace>생산입고 관리</RouterLink></b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><statisticsIcon class="icon" />통계</template>
          <b-dropdown-item href="#">재고관리 현황</b-dropdown-item>
          <b-dropdown-item href="#">영업관리 현황</b-dropdown-item>
          <b-dropdown-item href="#">구매관리 현황</b-dropdown-item>
          <b-dropdown-item href="#">기타 현황</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
    <!-- Right aligned nav items -->
    <b-navbar-nav class="ml-auto">
      <b-nav-item href="#"><chatbotIcon class="icon-right" /></b-nav-item>
      <b-nav-item href="#"><notificationIcon class="icon-right" /></b-nav-item>
      <b-nav-item href="#"> <loginIcon class="icon-right" /></b-nav-item>
    </b-navbar-nav>
  </b-navbar>
</template>
<style scoped>
.logo-img {
  height: 50px; /* 로고 크기 */
}

.icon {
  margin-right: 8px; /* 아이콘과 텍스트 간 간격 */
  width: 20px;
  height: 20px;
  fill: #333; /* 아이콘 색상 */
}

.icon-right {
  margin-right: 8px; /* 아이콘과 텍스트 간 간격 */
  width: 30px;
  height: 30px;
  fill: #333; /* 아이콘 색상 */
}

.header {
  background-color: #FFF9EA;
}
</style>
