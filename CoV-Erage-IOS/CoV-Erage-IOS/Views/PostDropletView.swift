//
//  SwiftUIView.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import SwiftUI

struct PostDropletView: View {
    
    @Binding var isPresented: Bool
    let startscreenVM: StartscreenVM?
    @ObservedObject private var postDropletVM = PostDropletVM()
    
    var body: some View {
        NavigationView {
            VStack {
                Form {
                    
                    Section(header: Text("Über Dich").font(.body)) {
                        TextField("Geschlecht", text: self.$postDropletVM.currentGender)
                        
                        HStack {
                            Text("Postleitzahl: ")
                            TextField("Postleitzahl", value: self.$postDropletVM.currentPostalCode, formatter: NumberFormatter())
                        }
                        
                        HStack {
                            Text("Geburtsjahr: ")
                            TextField("Geburtsjahr", value: self.$postDropletVM.currentYearOfBirth, formatter: NumberFormatter())
                        }
                    }
                    
                    Section(header: Text("Gesundheitszustand").font(.body)) {
                        Section(header: Text("Allgemeinbefinden").font(.body)) {
                            Picker("",selection: self.$postDropletVM.currentHealthState) {
                                Text("1").tag(1)
                                Text("2").tag(2)
                                Text("3").tag(3)
                                Text("4").tag(4)
                                Text("5").tag(5)
                            }.pickerStyle(SegmentedPickerStyle())
                        }
                        
                        HStack {
                            Text("Temperatur: \(self.postDropletVM.currentTemperature.formatted())° C")
                            Slider(value: self.$postDropletVM.currentTemperature, in: 30...45, step: 0.1)
                        }
                        
                        Section(header: Text("Husten").font(.body)) {
                            Picker("",selection: self.$postDropletVM.currentCoughing) {
                                Text("Keinen").tag(1)
                                Text("Wenig").tag(2)
                                Text("Viel").tag(3)
                            }.pickerStyle(SegmentedPickerStyle())
                        }
                        
                    }
                    
                }
                
                HStack {
                    Button("Absenden") {
                        self.postDropletVM.postDroplet()
                        self.isPresented = false
                        self.startscreenVM?.updateSubmittedStats()
                    }
                }.padding(EdgeInsets(top: 12, leading: 100, bottom: 12, trailing: 100))
                .foregroundColor(Color.white)
                .background(Color(red: 46/255, green: 204/255, blue: 113/255))
                .cornerRadius(10)
            }.navigationBarTitle("Tägliche Umfrage")
            
        }
    }
}

extension Double {
    func formatted() -> String {
        return String(format: "%.1f", self)
    }
}

struct PostDropletView_Previews: PreviewProvider {
    static var previews: some View {
        PostDropletView(isPresented: .constant(false), startscreenVM: nil)
    }
}
