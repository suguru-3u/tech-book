class Player {
  private readonly playerArmPower: number;
  private readonly playerWeaponPower: number;
  private readonly minPowerPoint = 0;
  private readonly maxPowerPoint = 1000;

  constructor(playerArmPower: number, playerWeaponPower: number) {
    if (!this.isInRange(playerArmPower)) {
      throw new Error("playerArmPowerに不正な値が混入されています");
    }
    if (!this.isInRange(playerWeaponPower)) {
      throw new Error("playerWeaponPowerに不正な値が混入されています");
    }
    this.playerArmPower = playerArmPower;
    this.playerWeaponPower = playerWeaponPower;
  }

  private isInRange(value: number): boolean {
    return value >= this.minPowerPoint && value <= this.maxPowerPoint;
  }

  public attackAmount(): number {
    return this.playerArmPower + this.playerWeaponPower;
  }
}

class Enemy {
  private readonly enemyBodyDefence: number;
  private readonly enemyArmorDefence: number;
  private hp: number = 300;
  private readonly minDefencePoint = 0;
  private readonly maxDefencePoint = 1000;

  constructor(enemyBodyDefence: number, enemyArmorDefence: number) {
    if (!this.isInRange(enemyBodyDefence)) {
      throw new Error("enemyBodyDefenceに不正な値が混入されています");
    }
    if (!this.isInRange(enemyArmorDefence)) {
      throw new Error("enemyArmorDefenceに不正な値が混入されています");
    }
    this.enemyBodyDefence = enemyBodyDefence;
    this.enemyArmorDefence = enemyArmorDefence;
  }

  private isInRange(value: number): boolean {
    return value >= this.minDefencePoint && value <= this.maxDefencePoint;
  }

  private defenceAmount(): number {
    return this.enemyBodyDefence + this.enemyArmorDefence;
  }

  public damage(player: Player): void {
    const minAttackPoint = 0;
    const damageTotal = player.attackAmount() - this.defenceAmount();
    if (damageTotal > minAttackPoint) {
      this.hp -= damageTotal;
    }
  }

  public getHp(): number {
    return this.hp;
  }
}

// 使用例
function mainV2(): void {
  const player = new Player(100, 50);
  const enemy = new Enemy(30, 20);

  enemy.damage(player);
  console.log(`Enemy HP: ${enemy.getHp()}`);
}

function main(): void {
    const playerArmPower = 100
    const playerWeaponPower = 50

    const enemyBodyDefence = 30
    const enemyArmorDefence = 20

    let damageAmount = 0
    damageAmount = playerArmPower + playerWeaponPower
    damageAmount = damageAmount - ((enemyBodyDefence + enemyArmorDefence) / 2);
    if (damageAmount < 0) {
        damageAmount = 0;
    }
}