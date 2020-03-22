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
    @ObservedObject var postDropletVM: PostDropletVM
        
    var body: some View {
        NavigationView {
            VStack {
                Form {
                    AboutYouView(postDropletVM: self.postDropletVM)
                    GeneralHealthView(postDropletVM: self.postDropletVM)
                    PhysicalSymptombs(postDropletVM: self.postDropletVM)
                    MentalProblems(postDropletVM: self.postDropletVM)
                }
                
                HStack {
                    Button(action: sendDroplet) {
                        Text("Absenden").padding(EdgeInsets(top: 12, leading: 100, bottom: 12, trailing: 100))
                        .foregroundColor(Color.white)
                        .background(Color(red: 46/255, green: 204/255, blue: 113/255))
                        .cornerRadius(10)
                    }
                }
            }
            .navigationBarTitle("Tägliche Umfrage")
        }.navigationViewStyle(StackNavigationViewStyle())


    }

    
    func sendDroplet() {
        self.postDropletVM.postDroplet(startscreenVM: self.startscreenVM)
        self.isPresented = false
    }
}

extension Double {
    func formatted() -> String {
        if self == -1 {
            return "Keine Angabe"
        }
        return String(format: "%.0f", self)
    }
}

struct PostDropletView_Previews: PreviewProvider {
    static var previews: some View {
        PostDropletView(isPresented: .constant(false), startscreenVM: nil, postDropletVM: PostDropletVM())
    }
}

struct AboutYouView: View {
    
    @ObservedObject var postDropletVM: PostDropletVM
        
    var body: some View {
        Section(header: Text("Über Dich (Pflichtfelder)").font(.body), footer:
            VStack {
                Button("Keyboard ausblenden") {
                    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                }
                HStack {
                    Spacer()
                    Text("Alle weiteren Felder sind optional.")
                        .font(.caption)
                    Spacer()
                }
            }) {
            Section(header: Text("Geschlecht ").font(.body).fontWeight(.black)) {
                Picker("",selection: self.$postDropletVM.gender) {
                    Text("Weiblich").tag(1)
                    Text("Männlich").tag(2)
                    Text("Divers").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            HStack {
                Text("Postleitzahl 🏘: ")
                    .fontWeight(.black)
                TextField("Postleitzahl", text: self.$postDropletVM.postalCode).keyboardType(.numberPad)
            }
            HStack {
                Text("Geburtsjahr 🥳: ")
                    .fontWeight(.black)
                TextField("Geburtsjahr", text: self.$postDropletVM.yearOfBirth).keyboardType(.numberPad)
            }
        
        }
    }
}

struct GeneralHealthView: View {
    
    @ObservedObject var postDropletVM: PostDropletVM
    
    var body: some View {
        Section(header: Text("Allgemeine Gesundheit").font(.body)) {
            Picker("Allgemeines Wohlbefinden 💪",selection: self.$postDropletVM.generalHealth) {
                Text("Sehr gut").tag(5)
                Text("Gut").tag(4)
                Text("War schonmal besser").tag(3)
                Text("Schlecht").tag(2)
                Text("Sehr schlecht").tag(1)
            }
            
            Picker("Corona Virus Infizierung 🏥",selection: self.$postDropletVM.coronaVirus) {
                Text("Keine Angabe").tag(-1)
                Text("Ich weiß nicht").tag(5)
                Text("Nein, alles gut").tag(1)
                Text("Ich glaube ja").tag(2)
                Text("Ich bin positiv getested worden").tag(3)
                Text("Ich habe es überstanden (geheilt)").tag(4)
            }
            
            HStack {
                Text("Anzahl Kontakte (24h):")
                Spacer()
                Text("\(self.postDropletVM.numberOfContacts.formatted())").padding(.trailing, 15.0)
            }
            Slider(value: self.$postDropletVM.numberOfContacts, in: 0...1000, step: 1)

        }
    }
}

struct PhysicalSymptombs: View {
    
    @ObservedObject var postDropletVM: PostDropletVM
    
    var body: some View {
        Section(header: Text("Physische Symptome").font(.body)) {
            Section(header: Text("Husten 😷").font(.body)) {
                Picker("",selection: self.$postDropletVM.coughing) {
                    Text("Keinen").tag(1)
                    Text("Etwas").tag(2)
                    Text("Starken").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
            Section(header: Text("Fieber 🤒").font(.body)) {
                Picker("",selection: self.$postDropletVM.temperature) {
                    Text("Kein").tag(1)
                    Text("Etwas").tag(2)
                    Text("Hoch (<39°C)").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
            Section(header: Text("Halsschmerzen 🧣").font(.body)) {
                Picker("",selection: self.$postDropletVM.soreThroat) {
                    Text("Keine").tag(1)
                    Text("Etwas").tag(2)
                    Text("Starke").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
            Section(header: Text("Schnupfen 🤧").font(.body)) {
                Picker("",selection: self.$postDropletVM.runnyNose) {
                    Text("Keinen").tag(1)
                    Text("Etwas").tag(2)
                    Text("Starken").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
            Section(header: Text("Gliederschermzen 🦵").font(.body)) {
                Picker("",selection: self.$postDropletVM.limbPain) {
                    Text("Keine").tag(1)
                    Text("Etwas").tag(2)
                    Text("Starke").tag(3)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
            Section(header: Text("Durchfall 💩").font(.body)) {
                Picker("",selection: self.$postDropletVM.diarrhea) {
                    Text("Ja 💦").tag(1)
                    Text("Nein 🥨").tag(2)
                }.pickerStyle(SegmentedPickerStyle())
            }
            
        }
    }
}

struct MentalProblems: View {
    
    @ObservedObject var postDropletVM: PostDropletVM
    
    var body: some View {
        Section(header: Text("Psychische Symptome").font(.body)) {
            Picker("Einsamkeit 😑",selection: self.$postDropletVM.loneliness) {
                Text("Keine Angabe").tag(-1)
                Text("Alles allerbestens").tag(5)
                Text("Komme gut klar").tag(4)
                Text("Geht so").tag(3)
                Text("Ich fühle mich schlecht").tag(2)
                Text("Kaum auszuhalten").tag(1)
            }
            
            Picker("Schlafprobleme 🛌",selection: self.$postDropletVM.insomnia) {
                Text("Keine Angabe").tag(-1)
                Text("Besser als sonst").tag(5)
                Text("So wie sonst auch").tag(4)
                Text("Geht so").tag(3)
                Text("Liege oft wach").tag(2)
                Text("Ich bekomme kein Auge zu").tag(1)
            }
            
        }
    }
}
