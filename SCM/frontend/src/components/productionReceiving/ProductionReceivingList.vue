<script setup>
import {onMounted, ref, watch} from 'vue';
import axios from "axios";

const totalCount = ref(0);
const pageSize = ref(10);
const pageNumber = ref(1);
const productionReceivingList = ref([]);
const productionReceivingStatusList = ref([]);
const searchStartDate = ref(null);
const searchEndDate = ref(null);
const searchName = ref(null);
const searchStatus = ref(new Set([]));

const fetchProductionReceivingList = async () => {
  try {
    const response = await axios.get(`http://localhost:8090/api/v1/productionReceiving`, {
      params: {
        searchStartDate: searchStartDate.value,
        searchEndDate: searchEndDate.value,
        searchName: searchName.value,
        searchStatus: searchStatus.value.size === 0 ? null : Array.from(searchStatus.value),
        page: pageNumber.value - 1, // Spring Pageable에서 0부터 시작
        size: pageSize.value
      }, paramsSerializer: (params) => {
        // null이나 undefined 값을 필터링
        const filteredParams = Object.fromEntries(
            Object.entries(params).filter(([_, value]) => value !== null && value !== undefined)
        );
        return new URLSearchParams(filteredParams).toString();
      }
    });

    productionReceivingList.value = response.data.productionReceivingList.content;
    productionReceivingStatusList.value = response.data.productionReceivingStatusList;
    totalCount.value = response.data.productionReceivingList.totalElements;

  } catch (error) {
    console.error("생산입고 불러오기 실패 :", error);
  }
};

onMounted(() => {
  fetchProductionReceivingList();
});

watch([searchStartDate, searchEndDate], () => {
  search();
})

watch(pageNumber, () => {
  fetchProductionReceivingList();
})

function check(status) {
  if(searchStatus.value.has(status)) {
    searchStatus.value.delete(status);
  } else {
    searchStatus.value.add(status);
  }

  search();
}

function search() {
  pageNumber.value = 1;

  fetchProductionReceivingList();
}
</script>

<template>
    <div class="row">
        <div class="col-md-3">
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">생산입고일</p>
                    <input type="date" v-model="searchStartDate"/> ~ <input type="date" v-model="searchEndDate"/>
                </div>
            </div>
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">생산입고명</p>
                    <b-input-group class="mt-3">
                        <b-form-input v-model="searchName"></b-form-input>
                        <b-button variant="light" class="button" @click="search()"><svg width="1em" id="Layer_1" version="1.1" viewBox="0 0 512 512" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><path d="M344.5,298c15-23.6,23.8-51.6,23.8-81.7c0-84.1-68.1-152.3-152.1-152.3C132.1,64,64,132.2,64,216.3  c0,84.1,68.1,152.3,152.1,152.3c30.5,0,58.9-9,82.7-24.4l6.9-4.8L414.3,448l33.7-34.3L339.5,305.1L344.5,298z M301.4,131.2  c22.7,22.7,35.2,52.9,35.2,85c0,32.1-12.5,62.3-35.2,85c-22.7,22.7-52.9,35.2-85,35.2c-32.1,0-62.3-12.5-85-35.2  c-22.7-22.7-35.2-52.9-35.2-85c0-32.1,12.5-62.3,35.2-85c22.7-22.7,52.9-35.2,85-35.2C248.5,96,278.7,108.5,301.4,131.2z"/></svg></b-button>
                    </b-input-group>
                </div>
            </div>
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">생산입고 상태</p>
                    <template v-for="productionReceivingStatus in productionReceivingStatusList">
                      <b-form-checkbox @click="check(productionReceivingStatus.key)">{{productionReceivingStatus.value}}</b-form-checkbox>
                    </template>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div>
              <div class="d-flex justify-content-between">
                  <div>검색결과: {{ totalCount }}개</div>
                  <b-button variant="light" size="sm" class="button">생산입고 등록</b-button>
              </div>
              <div class="list-headline row">
                  <div class="list-head col-4">생산입고명</div>
                  <div class="list-head col-2">생산공장명</div>
                  <div class="list-head col-2">보관창고명</div>
                  <div class="list-head col-3">입고일</div>
                  <div class="list-head col-1">상태</div>
              </div>
              <template v-if="productionReceivingList.length > 0">
                <div style="max-height: 600px; overflow-y: auto;">
                  <div v-for="productionReceiving in productionReceivingList" :key="productionReceiving.productionReceivingSeq" class="list-line row" @click="itemExtend">
                    <div class="list-body col-4 left">
                      {{ productionReceiving.productionReceivingName }}
                      <div v-if="productionReceiving.productionReceivingItemList.length > 0">
                        <template v-for="(productionReceivingItem, index) in productionReceiving.productionReceivingItemList" :key="productionReceivingItem.productionReceivingItemSeq">
                          <template v-if="index === productionReceiving.productionReceivingItemList.length - 1">
                            {{productionReceivingItem.itemName}}
                          </template>
                          <template v-else>
                            {{productionReceivingItem.itemName + ", "}}
                          </template>
                        </template>
                      </div>
                    </div>
                    <div class="list-body col-2">{{ productionReceiving.productionWarehouseName }}</div>
                    <div class="list-body col-2">{{ productionReceiving.storeWarehouseName }}</div>
                    <div class="list-body col-3">{{ productionReceiving.productReceivingRegDate }}</div>
                    <div class="list-body col-1">{{ productionReceiving.productionReceivingStatus }}</div>
                  </div>
                </div>
                <div class="pagination">
                  <b-pagination
                      v-model="pageNumber"
                      :totalRows="totalCount"
                      :perPage="pageSize">
                  </b-pagination>
                </div>
              </template>
              <template v-else>
                <b-card-text class="no-list-text">해당 검색조건에 부합한 생산입고가 존재하지 않습니다.</b-card-text>
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
    margin-left:1px;
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

</style>
