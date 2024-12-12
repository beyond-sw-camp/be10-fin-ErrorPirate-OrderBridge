export default [
    {
        path: "/shipping-instruction",
        component: () => import("@/views/shippingInstruction/ShippingInstructionListView.vue")
    },
    {
        path: "/shipping-instruction/input",
        component: () => import("@/views/shippingInstruction/ShippingInstructionInputView.vue")
    }
]