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
    <b-navbar-brand href="/">
      <img src="@/images/logo.png" alt="OrderBridge Logo" class="logo-img" />
    </b-navbar-brand>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown>
          <template #button-content><basicIcon class="icon"/>기본등록</template>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>창고 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>거래처 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="/item" class="none-line" active-class="active">
            <b-dropdown-item>품목 관리</b-dropdown-item>
          </RouterLink>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><salesIcon class="icon" />영업관리</template>
          <RouterLink to="/quotation" class="none-line" active-class="active">
            <b-dropdown-item>견적 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>주문서 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>판매 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="/shipping-instruction" class="none-line" active-class="active">
            <b-dropdown-item>출하지시서 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>출하 관리</b-dropdown-item>
          </RouterLink>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><orderIcon class="icon" />주문관리</template>
          <RouterLink to="/purchaseOrder" class="none-line" active-class="active">
            <b-dropdown-item>발주서 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="/purchase" class="none-line" active-class="active">
            <b-dropdown-item>구매서 관리</b-dropdown-item>
          </RouterLink>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><productionIcon class="icon" />생산관리</template>
          <RouterLink to="/workOrder" class="none-line" active-class="active">
            <b-dropdown-item>작업지시서 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>생산불출 관리</b-dropdown-item>
          </RouterLink>
          <RouterLink to="/productionReceiving" class="none-line" active-class="active">
            <b-dropdown-item>생산입고 관리</b-dropdown-item>
          </RouterLink>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown>
          <template #button-content><statisticsIcon class="icon" />통계</template>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>재고관리 현황</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>영업관리 현황</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>구매관리 현황</b-dropdown-item>
          </RouterLink>
          <RouterLink to="/productionReceiving/situation" class="none-line" active-class="active">
            <b-dropdown-item>생산입고 현황</b-dropdown-item>
          </RouterLink>
          <RouterLink to="" class="none-line" active-class="active">
            <b-dropdown-item>기타 현황</b-dropdown-item>
          </RouterLink>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
    <!-- Right aligned nav items -->
    <b-navbar-nav class="ml-auto">
      <b-nav-item href="#"><chatbotIcon class="icon-right" /></b-nav-item>
      <b-nav-item href="#"><notificationIcon class="icon-right" /></b-nav-item>
      <b-nav-item href="#"><loginIcon class="icon-right" /></b-nav-item>
    </b-navbar-nav>
  </b-navbar>
</template>
<style scoped>
.logo-img {
  height: 36px; /* 로고 크기 */
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
  border-bottom: solid 1px silver;
}

.none-decoration {
  text-decoration: none;
}

.none-line {
  text-decoration-line: none;
}
</style>
