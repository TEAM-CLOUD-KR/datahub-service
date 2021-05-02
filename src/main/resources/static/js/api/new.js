function validate() {
    if (!api_view.api_name.value) {
        alert("생성할 API의 이름을 입력하세요!");
        api_view.api_name.focus();
        return false;
    }
    // if (api_view.use_dataset.value == "") {
    //     alert("대상 데이터셋을 선택하세요!");
    //     api_view.use_dataset.focus();
    //     return false;
    // }
    if (!api_view.api_name.value) {
        alert("컬럼을 선택하세요!");
        api_view.api_name.focus();
        return false;
    }
    if (!api_view.explain.value) {
        alert("생성할 API에 대해 설명해주세요!");
        api_view.explain.focus();
        return false;
    }
    alert("신청이 정상적으로 완료되었습니다.");
}