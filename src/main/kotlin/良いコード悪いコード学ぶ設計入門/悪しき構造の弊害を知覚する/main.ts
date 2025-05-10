/*
問題：
以下のようなインターフェース `UserData` を使って、ユーザー情報の登録処理を実装した開発者がいました。

このプログラムには以下のような設計上の課題があります：
- 値の検証がオブジェクト生成時ではなく使用時に行われているため、バグの温床になりやすい。
- 不明瞭な変数名やネストされた条件分岐があり、可読性が低い。
- `UserData` はただのデータコンテナであり、振る舞いを持たないため、データ処理の責任が分散しています。

あなたのタスクは、以下の問題のある実装を読み、設計を改善した上で、ユーザー登録処理をより良い構造で書き直すことです。

要件：
- ユーザーは `name` と `age` を持ちます。
- 名前は空であってはならず、年齢は18歳以上でなければなりません。
- 無効な入力には例外を投げてください。
- 値の正当性は適切な箇所（インスタンス化時）で検証されるべきです。

下記のコードは問題のある例です。このコードを改善してください。
*/

// 問題のあるコード（これを改善してください）
interface UserData {
  name: string;
  age: number;
}

function registerUser(user: UserData): void {
  if (user.name !== "") {
    if (user.age >= 18) {
      console.log(`User ${user.name} is registered.`);
    } else {
      console.log("Age must be at least 18.");
    }
  } else {
    console.log("Name must not be blank.");
  }
}

const user1 = { name: "Alice", age: 20 };
registerUser(user1);

const user2 = { name: "", age: 25 };
registerUser(user2);

const user3 = { name: "Bob", age: 15 };
registerUser(user3);



class User {
  readonly name: string;
  readonly age: number;

  constructor(name: string, age: number) {
    if (name.trim() === "") {
      throw new Error("Name must not be blank.");
    }
    if (age < 18) {
      throw new Error("Age must be at least 18.");
    }
    this.name = name;
    this.age = age;
  }

  register(): void {
    console.log(`User ${this.name} is registered.`);
  }
}

// 使用例
try {
  const user1 = new User("Alice", 20);
  user1.register();
} catch (e) {
  console.error(e.message);
}

try {
  const user2 = new User("", 25);
  user2.register();
} catch (e) {
  console.error(e.message);
}

try {
  const user3 = new User("Bob", 15);
  user3.register();
} catch (e) {
  console.error(e.message);
}