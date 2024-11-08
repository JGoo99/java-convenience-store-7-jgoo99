# java-convenience-store-precourse

## 과제 요구사항

- [x] 입출력을 담당하는 클래스를 별도로 구현한다. (InputView, OutputView)
- [x] 현재 날짜와 시간을 가져오려면 DateTimes의 now()를 활용한다.
- [ ] 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
- [ ] 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.

# 기능 요구사항

#### 파일 리더기
- [x] name,price,quantity,promotion 정보를 읽어 하나의 Product 객체를 반환한다.
- [x] products.md 파일을 읽어 Product 객체 리스트를 반환한다.

#### 아이템
- [x] "[상품명-수량]" 문자열로 입력되면 이름과 수량 정보를 추출하여 객체를 생성한다.

#### 입력
- [x] 구현에 필요한 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다. (값은 수정할 수 있다.)
- [x] 사용자로부터 입력받은 상품명과 수량을 반환한다. ([상품명-수량],[상품명-수량],...)
- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
- [x] 재고가 부족하면 증정상품 추가여부를 묻지 않는다.
- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
- [x] 추가 구매 여부를 입력 받는다.
- [x] 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 입력받는다. (Y, N)
- [x] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시킨다.
- [x] 에러 메시지는 "[ERROR]"로 시작한다.
- [x] 예외 발생 시 에러 메시지를 출력한다.
- [x] 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
- [x] 멤버십 할인 적용 여부를 입력 받는다.

#### 출력
- [x] 환영 인사를 출력한다.
- [x] 현재 재고를 출력한다.
- [x] 만약 재고가 0개라면 재고 없음을 출력한다.
- [x] 각 안내멘트를 출력한다.
- [x] 구매 내역 정보를 영수증으로 출력한다.
- [x] 증정 내역 정보를 영수증으로 출력한다.
- [ ] 산출한 금액 정보를 영수증으로 출력한다.

#### 기능
- [x] 프로모션 할인 정책을 반영한다.
- [ ] 멤버십 할인 정책을 반영한다.
- [ ] 상품의 가격과 수량을 기반으로 최종 결제 금액을 반환한다.

- 재고관리
- [x] 재고는 name, price, quantity, promotion 정보를 갖는다.
- [x] 결제된 수량만큼 재고를 차감한다. (기본 결제)
- [x] 구매 수량이 0 이하인 경우 예외가 발생한다.
- [x] 구매 수량이 재고 수량을 초과하는 경우 예외가 발생한다

- 프로모션 할인
- [x] 오늘 날짜가 프로모션 기간을 비교하여 할인 가능 여부를 반환한다.
- [x] 할인 가능 여부를 판별한다.
- [x] 프로모션 상품이 다 팔린 경우 일반 재고에서 차감한다.
- [x] 프로모션 날짜가 지난 경우 일반 가격으로 결제한다.
- [x] 할인이 가능한 경우 할인율을 적용한다.
- [x] N개 구매 시 1개는 무료 증정한다. (재고 차감, 구매 개수 증가)
- [x] 프로모션을 각각의 재고에 적용한다.
- [x] 동일 상품에 여러 프로모션이 적용되지 않는다.
- [x] 프로모션 기간 내인 경우 프로모션 재고를 차감한다.
- [x] 프로모션 기간 내일 때 프로모션 재고가 없다면 일반재고를 차감한다. (정가로 결제됨을 출력하고, 정가로 결재한다.)
- [x] 구매 수량이 재고 수량을 넘는지 구한다.
- [x] 할인이 적용되지 않는 수량을 구한다.
- [x] 무료로 증정된 수량을 구한다.
- [x] 할인이 적용된 수량을 구한다.
- [x] 총 구매 수량을 구한다.
- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
- [x] 혜택 없이 결제한다고 입력 시 프로모션 재고의 전부를 차감한다.
- [x] 혜택 없이 결제를 하지 않는다고 입력 시 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.

- 멤버십할인
- [x] 프로모션 적용 후 남은 금액을 합산한다.
- [ ] 멤버십 할인율을 적용한다.
- [ ] 프로모션 미적용 금액의 30%를 할인받는다.
- [ ] 최대 한도는 8,000원이다.







