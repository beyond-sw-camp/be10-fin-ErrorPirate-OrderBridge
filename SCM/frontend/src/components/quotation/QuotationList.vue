<script setup>
import { ref, onMounted, watch } from 'vue';
import searchIcon from "@/assets/searchIcon.svg"
import axios from "@/axios"
import dayjs from 'dayjs';

const quotationDetail = ref({});
const quotationExtended = ref({});
const quotationList = ref([]);
const quotationStatusList = ref([]);
const itemDivisionList = ref([])
const currentPage = ref(1);
const totalPage = ref(1);
const totalQuotation = ref(0);
const searchPage = ref(1);
const searchSize = ref(10);
const searchStartDate = ref(null);
const searchEndDate = ref(null);
const searchClient = ref(null);
const searchStatus = ref(new Set());

// 견적서 목록 요청
const fetchQuotationList = async () => {
    try {
        const response = await axios.get(`quotation`, {
            params: {
                page: searchPage.value,
                size: searchSize.value,
                startDate: searchStartDate.value,
                endDate: searchEndDate.value,
                clientName: searchClient.value,
                quotationStatus: searchStatus.value.size === 0 ? null : Array.from(searchStatus.value).join(",")
            }
        });

        quotationList.value = response.data.quotation;
        currentPage.value = response.data.currentPage;
        totalPage.value = response.data.totalPages;
        totalQuotation.value = response.data.totalQuotation;
    } catch (error) {
        console.log(`견적서 목록 요청 실패`, error);
    }
}

// 견적서 상세 요청
const fetchQuotation = async (quotationSeq) => {
    try {
        const response = await axios.get(`quotation/${quotationSeq}`);

        quotationDetail.value[quotationSeq] = response.data;
        quotationExtended.value[quotationSeq] = true;
    } catch (error) {
        console.error(`견적서 상세 요청 실패 ${error}`);
    }
}

// 견적서 상태 분류 목록 요청
const fetchQuotationStatus = async () => {
    try { 
        const response = await axios.get(`quotation/status`);
        
        quotationStatusList.value = response.data;
    } catch (error) {
        console.log(`견적서 상태 분류 목록 요청 실패`, error);
    }
}

// 품목 분류 요청
const fetchItemDivision = async () => {
    try {
        const response = await axios.get(`item/item-division`);

        itemDivisionList.value = response.data;
    } catch (error) {
        console.log(`품목 분류 요청 실패 ${error}`);
    }
}

// 견적서 목록 엑셀 다운로드
const excelDown = async () => {
    const response = await axios.get(`quotation/excel`, {
        params: {
            startDate: searchStartDate.value,
            endDate: searchEndDate.value,
            clientName: searchClient.value,
            quotationStatus: searchStatus.value.size === 0 ? null : Array.from(searchStatus.value).join(",")
        }, responseType: "blob"
    });

    const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    });

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = decodeURIComponent(response.headers["content-disposition"].split('filename=')[1]);
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
}

onMounted(() => {
    fetchQuotationList();
    fetchQuotationStatus();
    fetchItemDivision();
});

watch([searchStartDate, searchEndDate, searchStatus.value], () => {
    search();
});

watch(searchPage, () => {

    fetchQuotationList();
});

// 상태 체크박스
function statusCheck(status) {
    searchStatus.value.has(status) ? searchStatus.value.delete(status)
                                   : searchStatus.value.add(status);
}

// 검색
function search() {
    searchPage.value = 1;

    fetchQuotationList();
}

// 상태 키로 값 반환
function findStatusValue(array, key) {
    for (const item of array) {
        if (item.key === key) {
            return item.value
        }
    }
}

// 견적서 클릭
function quotationClick(quotationSeq) {
    if (quotationExtended.value[quotationSeq]) {
        delete quotationExtended.value[quotationSeq];
        delete quotationDetail.value[quotationSeq];
    } else {
        fetchQuotation(quotationSeq);
    }
}

// 숫자 쉼표 삽입
function numberThree(number) {
    return `${number.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}`;
}

</script>

<template>
    <div class="row">
        <div class="col-md-3">
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">견적일</p>
                    <input type="date" v-model="searchStartDate" style="max-width: 40%;"/> ~ <input type="date" v-model="searchEndDate" style="max-width: 40%;"/>
                </div>
            </div>
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">거래처명</p>
                    <b-input-group class="mt-3">
                        <b-form-input v-model="searchClient"></b-form-input>
                        <b-button variant="light" class="button" @click="search()"><searchIcon class="icon"/></b-button>
                    </b-input-group>
                </div>
            </div>
            <div class="side-box card">
                <div class="card-body">
                    <p class="card-title">견적서 상태</p>
                    <template v-for="quotationStatus in quotationStatusList">
                        <b-form-checkbox @click="statusCheck(quotationStatus.key)">{{ quotationStatus.value }}</b-form-checkbox>
                    </template>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div style="width: 100%;">
                <div class="d-flex justify-content-between">
                    <div>검색결과: {{ totalQuotation }}개</div>
                    <div class="d-flex justify-content-end mt-3">
                        <b-button @click="excelDown()" variant="light" size="sm" class="button">엑셀 다운로드</b-button>
                        <b-button variant="light" size="sm" class="button ms-2">견적서 등록</b-button>
                    </div>
                </div>
                <div class="list-headline row">
                    <div class="list-headvalue col-6">견적서</div>
                    <div class="list-headvalue col-2">거래처</div>
                    <div class="list-headvalue col-2">견적일</div>
                    <div class="list-headvalue col-2">상태</div>
                </div>
                <template v-if="quotationList.length > 0">
                <div style="max-height: 600px; overflow-y: auto;">
                    <div v-for="quotation in quotationList" :key="quotation.quotationSeq" class="list-line row" @click="quotationClick(quotation.quotationSeq)">
                        <div class="col-6">
                            <b>{{ quotation.quotationName }}</b>
                            <div v-if="!quotationExtended[quotation.quotationSeq]">{{ quotation.itemName }}</div>
                        </div>
                        <div class="list-value col-2">{{ quotation.clientName }}</div>
                        <div class="list-value col-2">{{ quotation.quotationQuotationDate }}</div>
                        <div class="list-value col-2">{{ findStatusValue(quotationStatusList, quotation.quotationStatus) }}</div>

                        <div class="d-flex justify-content-center">
                            <div v-if="quotationExtended[quotation.quotationSeq]" class="col-md-11 mt-3">
                                <b>총 수량</b>: {{ numberThree(quotationDetail[quotation.quotationSeq].quotationTotalQuantity) }} 개<br>
                                <b>총 금액</b>: {{ `\\ ` + numberThree(quotationDetail[quotation.quotationSeq].quotationExtendedPrice) }}<br>
                                <b>담당자</b>: {{ quotationDetail[quotation.quotationSeq].userName }}<br>
                                <b>견적일시</b>: {{ dayjs(quotationDetail[quotation.quotationSeq].quotationQuotationDate).format(`YYYY/MM/DD HH:mm:ss`) }}<br>
                                <b>유효일시</b>: {{ dayjs(quotationDetail[quotation.quotationSeq].quotationEffectiveDate).format(`YYYY/MM/DD HH:mm:ss`) }}<br>
                                <b>비고</b>: {{ quotationDetail[quotation.quotationSeq].quotationNote }}<br>
                                <div style="display:flex; flex-wrap: wrap;">
                                <template v-for="quotationItem in quotationDetail[quotation.quotationSeq].quotationItem">
                                        <div class="card item-card">
                                            <img :src=quotationItem.itemImageUrl class="card-img-top">
                                            <div style="margin: 5px;">
                                                <small>{{ findStatusValue(itemDivisionList, quotationItem.itemDivision) }}</small>
                                                <div style="display: flex; justify-content: space-between;">
                                                    <b style="font-size: medium;">{{ quotationItem.itemName }}</b>
                                                    <small>{{ numberThree(quotationItem.quotationItemQuantity * quotationItem.quotationItemPrice) }} 원</small>
                                                </div>
                                                <small>{{ numberThree(quotationItem.quotationItemQuantity) }}개 / 개당 {{ numberThree(quotationItem.quotationItemPrice) }}원</small><br><br>
                                                <small style="margin-top: 20px;">비고: {{ quotationItem.quotationItemNote }}</small>
                                            </div>
                                        </div>
                                </template>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                </template>
                <template v-else>
                    <b-card-text class="no-list-text">해당 검색 조건에 부합하는 견적서가 존재하지 않습니다.</b-card-text>
                </template>
            </div>
            <div class="pagenation">
                <b-pagination
                    v-model="searchPage"
                    :total-rows="totalQuotation"
                    :per-page="searchSize">
                </b-pagination>
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

.list-headvalue {
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

.list-value {
    text-align: center;
    margin: auto 0;
    overflow: hidden;
    word-break: keep-all;
}

.pagenation {
    justify-items: center;
    margin-top: 20px;
}

.no-list-text {
    text-align: center;
    margin-top: 100px;
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
