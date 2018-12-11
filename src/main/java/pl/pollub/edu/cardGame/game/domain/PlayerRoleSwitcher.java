package pl.pollub.edu.cardGame.game.domain;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
final class PlayerRoleSwitcher {

    void drawPlayerWhoStartFirst(List<Player> players) {
        Random rand = new Random();
        Player startingFirstPlayer = players.get(rand.nextInt(players.size()));
        startingFirstPlayer.startAttack();
        for(Player player : players) {
            if(player.equals(startingFirstPlayer)) {
                player.startBePassive();
            }
        }
    }

    void switchRoundsAfterAttack(List<Player> players) {
        getAttacker(players).startBePassive();
        getPassivePlayer(players).startDefense();
    }

    void switchRoundsAfterDefense(List<Player> players) {
        getDefender(players).startBePassive();
        getPassivePlayer(players).startAttack();
    }

    void switchRoundsAfterStoppedAttack(List<Player> players) {
        getPassivePlayer(players).startAttack();
        getAttacker(players).startBePassive();
    }

    void switchRoundsAfterStoppedDefense(List<Player> players) {
        getDefender(players).startBePassive();
    }

    Player getAttacker(List<Player> players) {
        return players.stream()
                .filter(Player::isAttacker)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Game does not contain attacker"));
    }

    Player getDefender(List<Player> players) {
        return players.stream()
                .filter(Player::isDefender)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Game does not contain defender"));
    }

    Player getPassivePlayer(List<Player> players) {
        return players.stream()
                .filter(Player::isPassive)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Game does not contain passive player"));
    }
}
