package dev.fenixsoft.toa.stats;

public class Stats {

    float speed;

    float attack;
    float attackSpeed;

    float hitPoints;
    float armour;

    float mana;
    float stamina;

    public Stats(float speed, float attack, float attackSpeed, float hitPoints, float armour, float mana, float stamina) {
        this.speed = speed;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.hitPoints = hitPoints;
        this.armour = armour;
        this.mana = mana;
        this.stamina = stamina;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(float hitPoints) {
        this.hitPoints = hitPoints;
    }

    public float getArmour() {
        return armour;
    }

    public void setArmour(float armour) {
        this.armour = armour;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = stamina;
    }

}
