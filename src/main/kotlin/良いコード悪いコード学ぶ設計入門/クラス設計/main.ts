class Currency {
  constructor(public readonly name: string) {
    if (!name.trim()) {
      throw new Error("通貨名は空白にできません");
    }
  }
}

class Money {
  constructor(public readonly amount: number, public readonly currency: Currency) {}
}

class MoneyV2 {
  private readonly minAmount = 0;
  public readonly amount: number;
  public readonly currency: Currency;

  constructor(amount: number, currency: Currency) {
    if (amount < this.minAmount) {
      throw new Error("amountに不正な値が設定されています");
    }
    if (currency === null || currency === undefined) {
      throw new Error("currencyにnullまたはundefinedが設定されています");
    }
    this.amount = amount;
    this.currency = currency;
  }

  add(money: MoneyV2): MoneyV2 {
    if (money.currency.name !== this.currency.name) {
      throw new Error("通貨の単位が異なります");
    }
    return new MoneyV2(this.amount + money.amount, this.currency);
  }
}
