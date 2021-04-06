/*
    Copyright (c) 2021 Aaron(JIN, Taeyang).
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Lesser General Public License, version 3
    which accompanies this distribution, and is available at
    https://www.gnu.org/licenses/lgpl-3.0.html
    
    Contributors:
        Aaron(JIN, Taeyang) - 
*/

package kr.dataportal.datahubservice.domain.datacore;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class JSONResponse {
    private final HttpStatus status;
    private final LocalDateTime at;
    private final Object data;

    public JSONResponse() {
        this.status = null;
        this.at = null;
        this.data = null;
    }

    public JSONResponse(HttpStatus status, Object data) {
        this.status = status;
        this.at = LocalDateTime.now();
        this.data = data;
    }
}
