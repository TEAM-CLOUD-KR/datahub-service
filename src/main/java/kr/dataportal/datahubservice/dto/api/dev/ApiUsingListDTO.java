/*
    Copyright (c) 2021 Aaron(JIN, Taeyang).
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Lesser General Public License, version 3
    which accompanies this distribution, and is available at
    https://www.gnu.org/licenses/lgpl-3.0.html
    
    Contributors:
        Aaron(JIN, Taeyang) - 
*/

package kr.dataportal.datahubservice.dto.api.dev;

import kr.dataportal.datahubservice.dto.api.ApiList;
import kr.dataportal.datahubservice.dto.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiUsingListDTO {
    private final int seq;

    private final ApiList api;
    private final User requestUser;
    private final String serviceKey;
    private final ApiUsingAcceptEnum accept;
}
