////
////  MainIosViewModel.swift
////  iosApp
////
////  Created by Daffa Muhtar on 12/09/23.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import Foundation
//import shared
//
//extension ContentView {
//    @MainActor class HomeViewModel: ObservableObject {
//        
//        @Published private(set) var loginState:[LoginState] = []
//        @Published private(set) var isLoading: Bool = false
//        @Published private(set)
//        
//        private var currentPage = 1
//        private(set) var loadFinished: Bool = false
//        
//        func loadMovies() async {
//            if isLoading {
//                return
//            }
//            
//            do {
//                
//                let movies = try await getMoviesUseCase.invoke(page: Int32(currentPage))
//                isLoading = false
//                loadFinished = movies.isEmpty
//                
//                currentPage += 1
//                
//                self.movies = self.movies + movies
//                
//            } catch {
//                isLoading = false
//                loadFinished = true
//                
//                print(error.localizedDescription)
//            }
//        }
//    }
//}
