<script setup>
import {onMounted, ref, watch} from 'vue';
import axios from "axios";
import searchIcon from "@/assets/searchIcon.svg";
import trashIcon from "@/assets/trashIcon.svg";
import editIcon from "@/assets/editIcon.svg";
import printIcon from "@/assets/printIcon.svg";
import dayjs from "dayjs";
import router from "@/router/index.js";

const totalCount = ref(0);
const pageNumber = ref(1);
const purchaseList = ref([]);
const searchStartDate = ref('');
const searchEndDate = ref('');
const searchName = ref('');
const searchStatus = ref([]);
const expandedIndex = ref(null);

const toggleDetails = (index) => {
  expandedIndex.value = expandedIndex.value === index ? null : index;
};

const fetchPurchaseList = async () => {
  try {
    const params = {
      searchStartDate: searchStartDate.value,
      searchEndDate: searchEndDate.value,
      searchName: searchName.value,
      searchStatus: searchStatus.value.toString(),
      pageNo: pageNumber.value,
    };

    const filteredParams = Object.fromEntries(
        Object.entries(params).filter(([_, value]) => value !== null && value !== undefined && value !== '')
    );

    const response = await axios.get(`http://localhost:8090/api/v1/purchase`, {
      params: filteredParams,
      paramsSerializer: (params) => {
        return new URLSearchParams(params).toString();
      }
    });

    console.log(filteredParams);
    console.log(response.data);
    purchaseList.value = response.data.purchaseResponseList; // 구매서 목록
    totalCount.value = response.data.pagination.totalCount;

  } catch (error) {
    console.error("구매서 불러오기 실패 :", error);
  }
};


const excelDown = async () => {
  const excelName = "구매서_" + new Date().getFullYear() + (new Date().getMonth() + 1) + new Date().getDay();
  try {
    const params = {
      searchStartDate: searchStartDate.value,
      searchEndDate: searchEndDate.value,
      searchName: searchName.value,
      searchStatus: searchStatus.value.toString(),
      pageNo: pageNumber.value,
    };

    const filteredParams = Object.fromEntries(
        Object.entries(params).filter(([_, value]) => value !== null && value !== undefined && value !== '')
    );

    const response = await axios.get(`http://localhost:8090/api/v1/purchase/excelDown`, {
      params: filteredParams
      , paramsSerializer: (params) => {
        return new URLSearchParams(params).toString();
      },
      responseType: "blob", // 중요: blob 형식으로 설정
    });

    // Blob 객체 생성 및 다운로드 처리
    const blob = new Blob([response.data], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = decodeURIComponent(excelName); // 파일명 디코딩
    link.click();

    // Blob URL 해제
    URL.revokeObjectURL(link.href);

  } catch (error) {
    console.error("구매서 엑셀다운로드 실패 :", error);
  }
}

onMounted(() => {
  fetchPurchaseList();
})

watch(
    [searchStartDate, searchEndDate],
    () => {
      search();
    }
);

function check(status) {
  const index = searchStatus.value.indexOf(status);
  if (index > -1) {
    searchStatus.value.splice(index, 1);
  } else {
    searchStatus.value.push(status);
  }

  search();
}

watch(pageNumber, () => {
  fetchPurchaseList();
})

function search() {
  pageNumber.value = 1;
  fetchPurchaseList();
}

function register() {
  router.push("/purchase/input");
}

const itemDelete = async (seq) => {
  const result = confirm("정말 삭제하시겠습니까?");
  if (result) {
    try {
      const response = await axios.delete(`http://localhost:8090/api/v1/purchase/${seq}`);
      alert('구매서의 상태가 변경되었습니다.');

      search(); // 삭제 후 목록 갱신
    } catch (error) {
      console.error("삭제 요청 실패:", error);
      alert('삭제에 실패했습니다. 다시 시도해주세요.');
    }
  }
};

// 인쇄 함수
const printItem = (index) => {
  const printContent = document.getElementById(`print-area-${index}`).innerHTML;
  const originalContent = document.body.innerHTML;

  // 선택된 영역만 표시
  document.body.innerHTML = printContent;

  window.print();

  // 원래 내용 복원
  document.body.innerHTML = originalContent;

  // Vue 리렌더링 방지
  location.reload();
};

</script>

<template>
  <div class="row">
    <div class="col-md-3">
      <div class="side-box card">
        <div class="card-body">
          <p class="card-title">구매 계약일</p>
          <input type="date" v-model="searchStartDate"/> ~ <input type="date" v-model="searchEndDate"/>
        </div>
      </div>
      <div class="side-box card">
        <div class="card-body">
          <p class="card-title">거래처명</p>
          <b-input-group class="mt-3">
            <b-form-input v-model="searchName"></b-form-input>
            <b-button variant="light" class="button" @click="search()">
              <searchIcon class="icon"/>
            </b-button>
          </b-input-group>
        </div>
      </div>
      <div class="side-box card">
        <div class="card-body">
          <p class="card-title">구매서 상태</p>
          <b-form-checkbox
              :checked="searchStatus.includes('PROGRESS')"
              @change="check('PROGRESS')">
            정산중
          </b-form-checkbox>

          <b-form-checkbox
              :checked="searchStatus.includes('COMPLETE')"
              @change="check('COMPLETE')">
            정산완료
          </b-form-checkbox>
        </div>
      </div>
    </div>
    <div class="col-md-9">
      <div>
        <div class="d-flex justify-content-between">
          <div>검색결과: {{ totalCount }}개</div>
          <div class="d-flex justify-content-end mt-3">
            <b-button @click="excelDown()" variant="light" size="sm" class="button">엑셀 다운로드</b-button>
            <b-button @click="register()" variant="light" size="sm" class="button ms-2">구매서 등록</b-button>
          </div>
        </div>
        <div class="list-headline row">
          <div class="list-head col-5">구매서명</div>
          <div class="list-head col-2">거래처명</div>
          <div class="list-head col-3">계약일</div>
          <div class="list-head col-2">상태</div>
        </div>
        <template v-if="purchaseList?.length > 0">
          <div style="max-height: 600px; overflow-y: auto;">
            <div v-for="(purchase, index) in purchaseList" :key="purchase.purchaseSeq || index"
                 class="list-line row" :id="'print-area-' + index" @click="toggleDetails(index)">
              <div class="list-body col-5 left">
                <b>{{ purchase.purchaseName }}</b>
                <div v-if="purchase.purchaseItemResponseList?.length > 0">
                  <template v-for="(purchaseItem, idx) in purchase.purchaseItemResponseList"
                            :key="purchaseItem.purchaseItemSeq || idx">
                    <span v-if="expandedIndex !== index">
                      {{ purchaseItem.itemName }}
                      <span v-if="idx < purchase.purchaseItemResponseList.length - 1">, </span>
                    </span>
                  </template>
                </div>
              </div>
              <div class="list-body col-2">{{ purchase.clientName }}</div>
              <div class="list-body col-3">
                {{ purchase.purchaseContractDate ? dayjs(purchase.purchaseContractDate).format('YYYY/MM/DD HH:mm:ss') : ' - ' }}
              </div>
              <div class="list-body col-2">{{ purchase.purchaseStatusValue }}</div>

              <!-- 확장된 상세 내용 -->
              <div class="d-flex justify-content-center">
                <div v-if="expandedIndex === index" class="col-md-11 mt-3">
                  <p>총금액 : {{
                      purchase.purchaseExtendedPrice.toLocaleString()
                    }} 원</p>
                  <p>담당자 : {{
                      purchase.userName
                    }}</p>
                  <p>등록일 : {{
                          purchase.purchaseRegDate ? dayjs(purchase.purchaseRegDate).format('YYYY/MM/DD HH:mm:ss') : ' - '
                    }}</p>
                  <p>수정일 : {{
                      purchase.purchaseModDate ? dayjs(purchase.purchaseModDate).format('YYYY/MM/DD HH:mm:ss') : ' - '
                    }}</p>
                  <p>
                    구매서 비고 :
                    {{ purchase.purchaseNote }}
                  </p>
                  <div style="display:flex; flex-wrap: wrap;">
                    <div v-for="(purchaseItem, idx) in purchase.purchaseItemResponseList"
                         :key="purchaseItem.purchaseItemSeq || idx"
                         class="card item-card">
                      <img
                          class="card-img-top"
                          :src="purchaseItem.itemImageUrl != null ? purchaseItem.itemImageUrl : 'https://picsum.photos/200/200'"
                          alt="Responsive image">
                      <div style="margin: 5px;">
                        <div style="display: flex; justify-content: space-between;">
                          <b style="font-size: medium;">{{ purchaseItem.itemName }}</b>
                        </div>
                        <small>입고 창고 : {{ purchaseItem.warehouseName }} </small><br />
                        <small>수량 : {{ purchaseItem.purchaseItemQuantity }} 개 </small>
                        <br><br>
                        <small v-if="purchaseItem.purchaseItemNote" style="margin-top: 20px;">
                          비고: {{ purchaseItem.purchaseItemNote }}</small>
                        <small v-else style="margin-top: 20px;">비고: 없음</small>
                      </div>
                    </div>
                  </div>
                  <div class="d-flex justify-content-end align-items-center">
                    <printIcon class="me-3 icon" @click.stop="printItem(index)"/>
                    <editIcon class="me-3 icon" @click.stop=""/>
                    <trashIcon class="icon" @click.stop="itemDelete(purchase.purchaseSeq)"/>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="pagination">
            <b-pagination
                v-model="pageNumber"
                :totalRows="totalCount"
                :perPage="10"
                @input="fetchPurchaseList">
            </b-pagination>
          </div>
        </template>
        <template v-else>
          <b-card-text class="no-list-text">해당 검색조건에 부합한 구매서가 존재하지 않습니다.</b-card-text>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>

div {
  font-size: small;
}

.button {
  background-color: #FFF8E7;
  border: 1px solid;
}

.side-box {
  min-height: 100px;
  margin-top: 20px;
}

.card-title {
  font-size: medium;
  font-weight: bold;
}

.list-headline {
  border-bottom: 1px solid black;
  margin-bottom: 10px;
  padding: 20px 40px 20px 20px;
}

.list-head {
  text-align: center;
}

.list-line {
  width: 99%;
  border: 1px solid Silver;
  border-radius: 8px;
  margin-left: 1px;
  margin-top: 20px;
  padding: 10px 5px 10px 5px;
}

.list-body {
  text-align: center;
  margin: auto 0;
}

.pagination {
  justify-content: center; /* 가로 중앙 정렬 */
  margin-top: 20px;
}

.left {
  text-align: left;
}

.no-list-text {
  text-align: center;
  margin-top: 10%;
}

.icon {
  width: 20px;
  height: 20px;
}

.item-card {
  width: 220px;
  margin: 10px;
}

.card-img-top {
  max-height: 80px;
  object-fit: cover;
}

</style>
