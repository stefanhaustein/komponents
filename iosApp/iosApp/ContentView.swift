import SwiftUI
import shared


// Sorry I am to stupid to set up a proper Swift UIKit project ¯\_(ツ)_/¯
struct DemoView: UIViewRepresentable {
    var container = UIView()
    var demo: Demo

    init() {
        let c = self.container
       // c.backgroundColor = UIColor.gray
        demo = Demo(kontext: Kontext(), display: { kView in
            let uiView = kView.getView()
            c.addSubview(uiView)
           // uiView.backgroundColor = UIColor.blue
         //   uiView.translatesAutoresizingMaskIntoConstraints = false
            uiView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
            uiView.setNeedsLayout()
        })
        demo.showMainMenu()
    }

    func makeUIView(context: Context) -> UIView {
  //      container.backgroundColor = UIColor.red
        container.layoutSubviews()
        return container
    }

    func updateUIView(_ uiView: UIView, context: Context) {
    }
}


struct ContentView: View {
    var body: some View {
        DemoView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        DemoView()
        ContentView()
    }
}
