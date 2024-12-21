<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import basicIcon from '@/assets/basicIcon.svg';
import salesIcon from '@/assets/salesIcon.svg';
import productionIcon from '@/assets/productionIcon.svg';
import orderIcon from '@/assets/orderIcon.svg';
import statisticsIcon from '@/assets/statisticsIcon.svg'

// 사이드바 열림/닫힘 상태
const isSidebar = ref(false);

// 현재 활성화된 메뉴 상태
const activeMenu = ref("");
const activeSubMenu = ref("");

// 메뉴 열고 닫기 함수
const toggleMenu = (menu) => {
  activeMenu.value = activeMenu.value === menu ? "" : menu;
};

const toggleSubMenu = (subMenu) => {
  activeSubMenu.value = activeSubMenu.value === subMenu ? "" : subMenu;
};
</script>

<template>
  <aside class="side-bar" style="z-index: 5" :class="{ open: isSidebar }" @mouseover="isSidebar = true" @mouseleave="isSidebar = false">
    <div class="icon-wrapper">
      <span v-if="!isSidebar"><basicIcon class="icon"/></span>
      <span v-if="!isSidebar"><salesIcon class="icon"/></span>
      <span v-if="!isSidebar"><orderIcon class="icon"/></span>
      <span v-if="!isSidebar"><productionIcon class="icon"/></span>
      <span v-if="!isSidebar"><statisticsIcon class="icon"/></span>
    </div>
    <ul class="menu-list">
      <!-- 기본등록 -->
      <li>
        <span @click="toggleMenu('basic')"><basicIcon class="icon"/>기본등록</span>
        <ul v-if="activeMenu === 'basic'">
          <li>
            <span @click="toggleSubMenu('item')">품목</span>
            <ul v-if="activeSubMenu === 'item'">
              <li>
                <RouterLink class="content-item" to="">
                  품목 관리
                </RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('client')">거래처</span>
            <ul v-if="activeSubMenu === 'client'">
              <li>
                <RouterLink class="content-item" to="">
                  거래처 관리
                </RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('warehouse')">창고</span>
            <ul v-if="activeSubMenu === 'warehouse'">
              <li>
                <RouterLink class="content-item" to="">
                  창고 관리
                </RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </li>
      <!-- 영업관리 -->
      <li>
        <span @click="toggleMenu('sales')"><salesIcon class="icon"/>영업관리</span>
        <ul v-if="activeMenu === 'sales'">
          <li>
            <span @click="toggleSubMenu('quotation')">견적서</span>
            <ul v-if="activeSubMenu === 'quotation'">
              <li>
                <RouterLink class="content-item" to="">견적서 조회</RouterLink>
                <RouterLink class="content-item" to="">견적서 등록</RouterLink>
                <RouterLink class="content-item" to="">견적서 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('salesOrder')">주문서</span>
            <ul v-if="activeSubMenu === 'salesOrder'">
              <li>
                <RouterLink class="content-item" to="">주문서 조회</RouterLink>
                <RouterLink class="content-item" to="">주문서 등록</RouterLink>
                <RouterLink class="content-item" to="">주문서 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('invoice')">판매</span>
            <ul v-if="activeSubMenu === 'invoice'">
              <li>
                <RouterLink class="content-item" to="">거래명세서 조회</RouterLink>
                <RouterLink class="content-item" to="">거래명세서 등록</RouterLink>
                <RouterLink class="content-item" to="">거래명세서 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('shippingInstruction')">출하지시서</span>
            <ul v-if="activeSubMenu === 'shippingInstruction'">
              <li>
                <RouterLink class="content-item" to="/shipping-instruction" active-class="active" replace>출하지시서 조회</RouterLink>
                <RouterLink class="content-item" to="/shipping-instruction/input" active-class="active" replace>출하지시서 등록</RouterLink>
                <RouterLink class="content-item" to="/shipping-instruction/situation" active-class="active" replace>출하지시서 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('shippingSlip')">출하</span>
            <ul v-if="activeSubMenu === 'shippingSlip'">
              <li>
                <RouterLink class="content-item" to="/shipping-slip" active-class="active" replace>출하전표 조회</RouterLink>
                <RouterLink class="content-item" to="/shipping-slip/situation" active-class="active" replace>출하전표 현황</RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </li>
      <!-- 주문관리 -->
      <li>
        <span @click="toggleMenu('order')"><orderIcon class="icon"/>주문관리</span>
        <ul v-if="activeMenu === 'order'">
          <li>
            <span @click="toggleSubMenu('item')">발주서</span>
            <ul v-if="activeSubMenu === 'item'">
              <li>
                <RouterLink class="content-item" to="/purchaseOrder">발주서 조회</RouterLink>
                <RouterLink class="content-item" to="/purchaseOrder/input">발주서 등록</RouterLink>
                <RouterLink class="content-item" to="/purchaseOrder/situation">발주서 현황</RouterLink>
                <RouterLink class="content-item" to="/purchaseOrder/stock/situation">미입고 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('client')">구매</span>
            <ul v-if="activeSubMenu === 'client'">
              <li>
                <RouterLink class="content-item" to="/purchase">구매서 조회</RouterLink>
                <RouterLink class="content-item" to="/purchase/input">구매서 등록</RouterLink>
                <RouterLink class="content-item" to="/purchase/situation">구매 현황</RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </li>
      <!-- 생산관리 -->
      <li>
        <span @click="toggleMenu('production')"><productionIcon class="icon"/>생산관리</span>
        <ul v-if="activeMenu === 'production'">
          <li>
            <span @click="toggleSubMenu('item')">작업지시서</span>
            <ul v-if="activeSubMenu === 'item'">
              <li>
                <RouterLink class="content-item" to="/workOrder" active-class="active" replace>작업지시서 조회</RouterLink>
                <RouterLink class="content-item" to="">작업지시서 등록</RouterLink>
                <RouterLink class="content-item" to="">작업지시서 작업처리</RouterLink>
                <RouterLink class="content-item" to="/workOrder/situation">작업지시서 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('client')">생산불출</span>
            <ul v-if="activeSubMenu === 'client'">
              <li>
                <RouterLink class="content-item" to="">생산불출 조회</RouterLink>
                <RouterLink class="content-item" to="">생산불출 등록</RouterLink>
                <RouterLink class="content-item" to="">생산불출 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('warehouse')">생산입고</span>
            <ul v-if="activeSubMenu === 'warehouse'">
              <li>
                <RouterLink class="content-item" to="/productionReceiving">생산입고 조회</RouterLink>
                <RouterLink class="content-item" to="/productionReceiving/register">생산입고 등록</RouterLink>
                <RouterLink class="content-item" to="/productionReceiving/situation">생산입고 현황</RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </li>
      <!-- 통계 -->
      <li>
        <span @click="toggleMenu('stats')"><statisticsIcon class="icon"/>통계</span>
        <ul v-if="activeMenu === 'stats'">
          <li>
            <span @click="toggleSubMenu('stock')">재고관리 현황</span>
            <ul v-if="activeSubMenu === 'stock'">
              <li>
                <RouterLink class="content-item" to="">품목별 창고 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('sale')">영업관리 현황</span>
            <ul v-if="activeSubMenu === 'sale'">
              <li>
                <RouterLink class="content-item" to="">견적서 현황</RouterLink>
                <RouterLink class="content-item" to="">주문서 현황</RouterLink>
                <RouterLink class="content-item" to="">거래명세서 현황</RouterLink>
                <RouterLink class="content-item" to="">출하지시서 현황</RouterLink>
                <RouterLink class="content-item" to="">출하전표 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('order')">주문관리 현황</span>
            <ul v-if="activeSubMenu === 'order'">
              <li>
                <RouterLink class="content-item" to="">발주 현황</RouterLink>
                <RouterLink class="content-item" to="">구매 현황</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <span @click="toggleSubMenu('production')">생산관리 현황</span>
            <ul v-if="activeSubMenu === 'production'">
              <li>
                <RouterLink class="content-item" to="/workOrder/situation">작업지시서 현황</RouterLink>
                <RouterLink class="content-item" to="">생산불출 현황</RouterLink>
                <RouterLink class="content-item" to="/productionReceiving/situation">생산입고 현황</RouterLink>
              </li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </aside>
</template>

<style scoped>
/* ul > li 스타일 */
.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-list > li {
  margin-bottom: 16px; /* li 간격 */
}

.icon-wrapper {
  display: flex;
  margin-top:76px;
  flex-direction: column;
  align-items: center;
  padding: 16px 0;
  gap: 16px; /* 아이콘 간 간격 */
}

.icon {
  margin-right: 8px; /* 아이콘과 텍스트 간 간격 */
  width: 20px;
  height: 20px;
  fill: #333; /* 아이콘 색상 */
}

.side-bar {
  position: fixed;
  top: 0; /* 헤더의 높이 */
  bottom: 0;
  height: 100%;
  width: 40px;
  background-color: #fff8e7;
  overflow: hidden;
  transition: width 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 0;
}

.side-bar.open {
  width: 200px;
}

.side-bar ul {
  margin: 0;
  padding: 0;
  list-style: none;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.side-bar.open ul {
  opacity: 1;
}

.side-bar ul > li > span {
  display: block;
  font-size: 18px;
  font-weight: bold;
  color: #aaaaaa;
  text-decoration: none;
  padding: 10px 10px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.side-bar ul > li > span:hover {
  color: black;
}

.side-bar ul ul > li > span {
  display: block;
  font-size: 14px;
  font-weight: normal;
  color: #aaaaaa;
  padding: 4px 20px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.side-bar ul ul > li > span:hover {
  color: black;
}

.content-item {
  display: block;
  font-size: 10px;
  color: #aaaaaa;
  padding: 5px 20px;
  text-decoration: none;
  transition: color 0.3s ease;
}

.content-item:hover {
  color: black;
}
</style>
