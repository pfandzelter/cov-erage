//
//  LastSubmittedTimerView.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 21.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import SwiftUI

struct LastSubmittedTimerView: View {
    
    let timeElapsed: String
    
    var body: some View {
        Text("Hello World")
    }
}

struct LastSubmittedTimerView_Previews: PreviewProvider {
    static var previews: some View {
        LastSubmittedTimerView(timeElapsed: "1min 30sec")
    }
}
