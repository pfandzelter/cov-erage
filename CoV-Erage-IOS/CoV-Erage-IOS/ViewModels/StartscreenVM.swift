//
//  StartscreenVM.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 21.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

class StartscreenVM : ObservableObject {
    
    @Published var displayedLastSubmitted: String = "Ausstehend"
    private var lastSubmitted: Date?
    var timer: Timer?
    
    @Published var numberOfSubmittedDroplets: Int = 0
    
    func readyToSubmitAgain() -> Bool {
        if let lastSubmitted = self.lastSubmitted {
            if Date().timeIntervalSince(lastSubmitted) > TimeInterval(60) {
                return true // more than 1min has past
            } else {
                return false // time is not up
            }
        } else {
            return true // nothing send yet
        }
    }
    
    func setFailed() {
        if timer != nil {
            timer?.invalidate()
        }
        
        self.lastSubmitted = nil
        self.displayedLastSubmitted = "Fehler"
    }
    
    func updateSubmittedStats() {
        if timer != nil {
            timer?.invalidate()
        }
        
        self.lastSubmitted = Date()
        
        timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { timer in
            if let lastSubmitted = self.lastSubmitted {
                self.displayedLastSubmitted = self.format(duration: Date().timeIntervalSince(lastSubmitted))
            } else {
                self.displayedLastSubmitted = "Ausstehend"
            }
        }
        
        numberOfSubmittedDroplets += 1
    }
    
    func format(duration: TimeInterval) -> String {
        let formatter = DateComponentsFormatter()
        formatter.allowedUnits = [.day, .hour, .minute, .second]
        formatter.unitsStyle = .short
        formatter.maximumUnitCount = 1

        return formatter.string(from: duration)!
    }
    
}
