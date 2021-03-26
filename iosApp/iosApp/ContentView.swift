import SwiftUI
import shared


// Sorry I am to stupid to set up a proper Swift UIKit project ¯\_(ツ)_/¯
struct DemoView: UIViewRepresentable {
    var container = UIView()
    var demo: Demo

    init() {
        let c = self.container
        // c.backgroundColor = UIColor.blue

        demo = Demo(kontext: Kontext(), display: { kView in
            for view in c.subviews {
                view.removeFromSuperview()
            }
            let uiView = kView.getView()
            c.addSubview(uiView)
            uiView.frame = c.bounds
            uiView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
            // uiView.backgroundColor = UIColor.red
            // uiView.translatesAutoresizingMaskIntoConstraints = false
            // uiView.setNeedsLayout()
        })
        demo.showMainMenu()
    }

    func makeUIView(context: Context) -> UIView {
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
