package com.luthynetwork.core.libs.scoreboard.settings;

import com.luthynetwork.core.libs.scoreboard.provider.CommonScoreboard;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Missionary (missionarymc@gmail.com)
 * @since 5/31/2018
 */
@Getter
@Builder
public class BoardSettings {

    private CommonScoreboard boardProvider;

    private ScoreDirection scoreDirection;

}
