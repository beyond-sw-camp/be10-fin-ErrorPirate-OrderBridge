<script setup>
import {defineProps, ref, watch} from "vue";
import trashIcon from '@/assets/trashIcon.svg'

const props = defineProps({
  itemList: {type: Array, required: true},       // 주문서 품목 리스트
  selectedSalesOrder: {type: Boolean},       // 주문서 선택 여부
});

const emit = defineEmits(['registerEvent', 'updateItemListEvent']);

// itemList의 각 아이템에 대해 itemData 초기화
const itemData = ref(props.itemList.map(item => ({
  originalQuantity: Number(item.salesOrderItemQuantity) || 0,
  Seq: Number(item.itemSeq) || 0,
  quantity: Number(item.salesOrderItemQuantity) || 0,
  note: item.salesOrderItemNote || '',
})));

// itemList 변경 시 itemData 업데이트
watch(() => props.itemList, (newItemList) => {
  // itemData 배열을 새 itemList에 맞게 재설정
  itemData.value = newItemList.map(item => ({
    originalQuantity: Number(item.salesOrderItemQuantity) || 0,
    Seq: Number(item.itemSeq) || 0,
    quantity: Number(item.salesOrderItemQuantity) || 0,
    note: item.salesOrderItemNote || '',
  }));
});

// 특정 아이템 삭제
const deleteItem = (index) => {
  // 아이템이 하나만 남아 있으면 삭제 불가능
  if (itemData.value.length <= 1) {
    alert("최소 하나의 품목은 등록해야 합니다."); // 사용자 알림
    return;
  }

  // itemData에서 해당 index 삭제
  itemData.value.splice(index, 1);

  // props.itemList를 업데이트하기 위해 부모 컴포넌트로 emit
  const updatedItemList = [...props.itemList];
  updatedItemList.splice(index, 1);
  emit('updateItemListEvent', updatedItemList); // 부모 컴포넌트에서 itemList 갱신
};

const addShippingInstruction = () => {
  emit('registerEvent', itemData);
}

// 품목 포맷 함수
const formatDivision = (divisionString) => {
  if (divisionString === 'FINISHED') {
    return '완제품';
  } else if (divisionString === 'RAW') {
    return '구성품';
  } else if (divisionString === 'PART') {
    return '부재료';
  } else if (divisionString === 'SUB') {
    return '원재료';
  }
  return divisionString; // 품목가 다른 경우 그대로 반환
}
</script>

<template>
  <template v-if="props.selectedSalesOrder">
    <div class="col-md-10 d-flex flex-column">
      <h5 class="px-4">출하지시 물품 등록</h5>
      <template v-if="itemList.length > 0">
        <div style="max-height: 250px; overflow-y: auto;">
          <div style="max-height: 200px;" v-for="(item, index) in itemList" :key="item.salesOrderItemSeq"
               class="mx-5 my-3 d-flex flex-row border border-secondary rounded">
            <b-img class="p-2 col-md-4" src="https://picsum.photos/200/200" fluid alt="Responsive image"></b-img>
            <div class="p-2 col-md-8">
              <div class="mb-4 d-flex justify-content-between">
                <span class="fw-bold">{{ item.itemName }}</span>
                <trashIcon class="icon" @click="deleteItem(index)"/>
              </div>
              <div class="d-flex">
                <ul class="col-md-6 p-0">
                  <li class="mb-3 ">
                    · 품목 구분 : {{ formatDivision(item.itemDivision) }}
                  </li>
                  <li class="mb-3 d-flex flex-row">
                    · 수량 :
                    <b-form-input class="ms-2 me-2 w-25" type="text" v-model="itemData[index].quantity"
                                  size="sm"></b-form-input>
                    개 (최대 : {{ itemData[index].originalQuantity }} 개)
                  </li>
                </ul>
                <ul class="col-md-6 p-0 d-flex flex-column justify-content-between">
                  <li class="mb-3 d-flex flex-row">
                    · 비고 :
                    <b-form-input class="ms-2 w-75" type="text" v-model="itemData[index].note" size="sm"></b-form-input>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </template>
      <template v-else>
        <b-card-text class="no-list-text">출하지시가 완료된 주문서 입니다. 다시 선택해 주세요</b-card-text>
      </template>
      <template v-if="itemList.length > 0">
        <div class="mx-5 my-3 d-flex justify-content-end">
          <b-button class="button mx-3" variant="light" size="sm" @click="addShippingInstruction">등록</b-button>
        </div>
      </template>
    </div>
  </template>
</template>

<style scoped>
li {
  list-style: none;
}

.button {
  background-color: #FFF8E7;
  border: 1px solid;
}

.icon {
  width: 20px;
  height: 20px;
}

.no-list-text {
  text-align: center;
  margin-top: 5%;
}
</style>