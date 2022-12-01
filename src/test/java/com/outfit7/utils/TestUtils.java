package com.outfit7.utils;

import com.outfit7.entity.User;
import com.outfit7.entity.User.Champion;
import com.outfit7.entity.User.Hero;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static User user() {
        return users().get(0);
    }

    public static List<User> users() {
        return List.of(
                User.builder().playerName("name1").powerLevel(10L).rank(10L).id("1").hero(hero(1L)).champions(champions()).build(),
                User.builder().playerName("name1").powerLevel(11L).rank(20L).id("2").hero(hero(2L)).champions(champions()).build(),
                User.builder().playerName("name2").powerLevel(7L).rank(30L).id("3").hero(hero(3L)).champions(champions()).build(),
                User.builder().playerName("name2").powerLevel(4L).rank(40L).id("3").hero(hero(3L)).champions(champions()).build(),
                User.builder().playerName("name3").powerLevel(40L).rank(50L).id("4").hero(hero(3L)).champions(champions()).build(),
                User.builder().playerName("name3").powerLevel(35L).rank(60L).id("4").hero(hero(3L)).champions(champions()).build(),
                User.builder().playerName("name4").powerLevel(8L).rank(70L).id("5").hero(hero(3L)).champions(champions()).build(),
                User.builder().playerName("name4").powerLevel(9L).rank(80L).id("6").hero(hero(4L)).champions(champions()).build(),
                User.builder().playerName("name5").powerLevel(30L).rank(250L).id("7").hero(hero(4L)).champions(champions()).build(),
                User.builder().playerName("name5").powerLevel(13L).rank(90L).id("7").hero(hero(5L)).champions(champions()).build());
    }

    public static Hero hero(Long level) {
        return Hero.builder()
                .id("HeroId" + level)
                .name("HeroName" + level)
                .level(level)
                .build();
    }

    public static List<Champion> champions() {
        List<Champion> champions = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            champions.add(Champion.builder()
                    .id("ChampionId" + i)
                    .name("ChampionName" + i)
                    .level((long) i)
                    .build());
        }
        return champions;
    }

}
